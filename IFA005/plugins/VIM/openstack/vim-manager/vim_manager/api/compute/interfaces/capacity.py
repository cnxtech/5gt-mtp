# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#
from urllib.parse import urlparse

import flask
import http
import json
import requests

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api.schema import CapacityInformation
from vim_manager.api.schema import NfviPop
from vim_manager.api.schema import ResourceZone
from vim_manager.api.schema import TimePeriodInformation
from vim_manager.osc.clients import OpenStackClients

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_capacity_management', __name__)


class QueryComputeCapacityRequest(Schema):
    zoneId = fields.Str(
        required=True,
        description='When specified this parameter identifies the resource '
                    'zone for which the capacity is requested. When not '
                    'specified the total capacity managed by the VIM instance '
                    'will be returned.')
    computeResourceTypeId = fields.Str(
        required=True,
        description='Identifier of the resource type for which the issuer '
                    'wants to know the available, total, reserved and/or '
                    'allocated capacity.')
    resourceCriteria = fields.Str(
        required=True,
        description='Input capacity computation parameter for selecting the '
                    'virtual memory, virtual CPU and acceleration '
                    'capabilities for which the issuer wants to know the '
                    'available, total, reserved and/or allocated capacity. '
                    'Selecting parameters/attributes that shall be used are '
                    'defined in the VirtualComputeResourceInformation, '
                    'VirtualCpuResourceInformation, and '
                    'VirtualMemoryResourceInformation information elements. '
                    'This information element and the computeResourceTypeId '
                    'are mutually exclusive.')
    attributeSelector = fields.Str(
        required=True,
        description='Input parameter for selecting which capacity information '
        '(i.e. available, total, reserved and/or allocated '
        'capacity) is queried. When not present, all four values '
        'are requested.')
    timePeriod = fields.Nested(
        TimePeriodInformation,
        required=True,
        description='The time interval for which capacity is queried. When '
                    'omitted, an interval starting "now" is used. The time '
                    'interval can be specified since resource reservations '
                    'can be made for a specified time interval.')


class CapacityQueryAPI(SwaggerView):
    """Query Compute Capacity operation.

    This operation supports retrieval of capacity information for the various
    types of consumable virtualised compute resources available in the
    Virtualised Compute Resources Information Management Interface.
    """

    parameters = [
        {
            "in": "body",
            "name": "QueryComputeCapacityRequest",
            "schema": QueryComputeCapacityRequest,
            "required": True
        }
    ]
    responses = {
        OK: {
            'description': 'The capacity during the requested time period. '
                           'The scope is according to parameter zoneId of the '
                           'request during the time interval.',
            'schema': CapacityInformation
        },
        UNAUTHORIZED: {
            "description": "Unauthorized",
        },
        FORBIDDEN: {
            "description": "Forbidden",
        },
        BAD_REQUEST: {
            "description": "Bad request",
        },
    }
    tags = ['virtualisedComputeResources']
    operationId = "queryComputeCapacity"

    def get(self):
        resource_type_input = flask.request.args.get('computeResourceTypeId')

        resource_types = {
            'VirtualCpuResourceInformation': 'VCPU',
            'VirtualMemoryResourceInformation': 'MEMORY_MB',
            'VirtualStorageResourceInformation': 'DISK_GB'
        }

        resource_type = resource_types.get(resource_type_input)

        config = flask.current_app.osloconfig
        session = OpenStackClients(config).session
        auth_headers = session.get_auth_headers()

        keystone = OpenStackClients(config).keystone()

        placement_service_id = keystone.services.find(name='placement').id
        placement_api_url = keystone.endpoints.find(
            service_id=placement_service_id).url

        url = placement_api_url + '/resource_providers'

        r = requests.get(url, headers=auth_headers)

        resource_providers = json.loads(r.text)

        url_inventory = None

        for resource_provider in resource_providers['resource_providers']:
            links = resource_provider['links']

            placement_api_path = urlparse(placement_api_url).path
            base_url = placement_api_url.split(placement_api_path)[0]
            for link in links:
                if link['rel'] == 'inventories':
                    url_inventory = base_url + link['href']

        r = requests.get(url_inventory, headers=auth_headers)

        data = json.loads(r.text)
        inventories = data['inventories'][resource_type]

        capacity_information = {
            'availableCapacity':
                inventories['total'] - inventories['reserved'],
            'reservedCapacity': None,  # May be use blazar lib
            'totalCapacity': inventories['total'],
            'allocatedCapacity': inventories['reserved']
        }

        return flask.jsonify(capacity_information), OK


class ComputeResourceZoneQueryAPI(SwaggerView):
    """Query Compute Resource Zone operation.

    This operation enables the NFVO to query information about a Resource
    Zone, e.g. listing the properties of the Resource Zone, and other metadata.
    """

    responses = {
        OK: {
            'description': 'The filtered information that has been retrieved '
                           'about the Resource Zone. The cardinality can be 0 '
                           'if no matching information exist.',
            'schema': {
                'type': 'array',
                'items': ResourceZone
            },
        },
        UNAUTHORIZED: {
            "description": "Unauthorized",
        },
        FORBIDDEN: {
            "description": "Forbidden",
        },
        BAD_REQUEST: {
            "description": "Bad request",
        },
    }
    tags = ['virtualisedComputeResources']
    operationId = "queryComputeResourceZone"

    def get(self):

        return flask.jsonify(''), OK


class NfviPopComputeInformationQueryAPI(SwaggerView):
    """Query NFVI-PoP Compute Information operation.

    This operation enables the NFVOs to query general information to the VIM
    concerning the geographical location and network connectivity endpoints to
    the NFVI-PoP(s) administered by the VIM, and to determine network
    endpoints to reach VNFs instantiated making use of virtualised compute
    resources in the NFVI as specified by the exchanged information elements.
    """

    responses = {
        OK: {
            'description': 'The filtered information that has been retrieved. '
                           'The cardinality can be 0 if no matching '
                           'information exist.',
            'schema': {
                'type': 'array',
                'items': NfviPop
            },
        },
        UNAUTHORIZED: {
            "description": "Unauthorized",
        },
        FORBIDDEN: {
            "description": "Forbidden",
        },
        BAD_REQUEST: {
            "description": "Bad request",
        },
    }
    tags = ['virtualisedComputeResources']
    operationId = "queryNFVIPoPComputeInformation"

    def get(self):

        return flask.jsonify(''), OK


blueprint.add_url_rule(
    '/compute_resources/capacities', strict_slashes=False,
    view_func=CapacityQueryAPI.as_view(
        'queryComputeCapacity'),
    methods=['GET']
)


blueprint.add_url_rule(
    '/compute_resources/resource_zones', strict_slashes=False,
    view_func=ComputeResourceZoneQueryAPI.as_view(
        'queryComputeResourceZoneQuery'),
    methods=['GET']
)

blueprint.add_url_rule(
    '/compute_resources/nfvi_pop_compute_information', strict_slashes=False,
    view_func=NfviPopComputeInformationQueryAPI.as_view(
        'queryNfviPopComputeInformation'),
    methods=['GET']
)
