# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#

import flask
import http

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api.resource_quota.schema import VirtualComputeQuota
from vim_manager.api.resource_quota.schema import VirtualComputeQuotaData
from vim_manager.api.schema import Identifier

from vim_manager.osc.clients import OpenStackClients

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_resources_quota', __name__)


class CreateComputeResourceQuotaRequest(Schema):
    resourceGroupId = fields.Str(
        required=True,
        description='Name provided by the consumer for the virtualised '
                    'compute resource to allocate. It can be used for '
                    'identifying resources from consumer side.')
    virtualComputeQuota = fields.Nested(
        VirtualComputeQuotaData,
        required=True,
        description='Identifier of the resource reservation applicable to '
                    'this virtualised resource management operation. '
                    'Cardinality can be 0 if no resource reservation was '
                    'used.')


class ComputeResourceQuotaCreateAPI(SwaggerView):
    """Create Compute Resource Quota operation.

    This operation allows requesting the quota of virtualised compute
    resources as indicated by the consumer functional block.
    """

    parameters = [
        {
            "name": "body",
            "in": "body",
            "schema": CreateComputeResourceQuotaRequest,
            "required": True
        }
    ]
    responses = {
        CREATED: {
            'description': 'Element containing information about the quota '
                           'resource.',
            'schema': VirtualComputeQuota,
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

    tags = ['virtualisedResourceQuota']
    operationId = "createComputeQuota"

    def post(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        data = flask.request.get_json()

        resource_group_id = data['resourceGroupId']
        compute_quota = data['virtualComputeQuota']

        resources = {}
        if compute_quota.get('numVCPUs'):
            resources['cores'] = compute_quota.get('numVCPUs')

        if compute_quota.get('numVcInstances'):
            resources['instances'] = compute_quota.get('numVcInstances')

        if compute_quota.get('virtualMemSize'):
            resources['ram'] = compute_quota.get('virtualMemSize')

        nova.quotas.update(resource_group_id, **resources)

        quotas = nova.quotas.get(resource_group_id)

        quotas_data = {
            'resourceGroupId': quotas.id,
            'numVCPUs': quotas.cores,
            'numVcInstances': quotas.instances,
            'virtualMemSize': quotas.ram
        }

        return flask.jsonify(quotas_data), CREATED


class ComputeResourceQuotaTerminateAPI(SwaggerView):
    """Terminate Compute Resource Quota operation.

    This operation allows terminating one or more issued compute resource
    quota(s). When the operation is done on multiple ids, it is assumed to be
    best-effort, i.e. it can succeed for a subset of the ids, and fail for
    the remaining ones.
    """

    parameters = [
        {
            'name': 'resourceGroupId',
            "in": "query",
            'type': 'array',
            'items': {'type': Identifier},
            'description': 'Unique identifier of the "infrastructure resource '
                           'group", logical grouping of virtual resources '
                           'assigned to a tenant within an Infrastructure '
                           'Domain.',
            'required': True
        }
    ]

    responses = {
        OK: {
            'description': 'Unique identifier of the "infrastructure resource '
                           'group", logical grouping of virtual resources '
                           'assigned to a tenant within an Infrastructure '
                           'Domain.',
            'schema': {
                'type': 'array',
                'items': {'type': Identifier}
                }
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

    tags = ['virtualisedResourceQuota']
    operationId = "terminateComputeQuota"

    def delete(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        resource_group_ids = flask.request.args.getlist('resourceGroupId')

        deleted_ids = []
        for resource_group_id in resource_group_ids:
            try:
                nova.quotas.delete(resource_group_id)
                deleted_ids.append(resource_group_id)
            except Exception:
                pass

        return flask.jsonify(deleted_ids), OK


class ComputeResourceQuotaQueryAPI(SwaggerView):
    """Query Compute Resource Quota operation.

    This operation allows querying quota information about compute resources
    that the consumer has access to.
    """

    responses = {
        OK: {
            'description': 'Element containing information about the quota '
                           'resource. The cardinality can be 0 if no matching '
                           'quota exists.',
            'schema': {
                'type': 'array',
                'items': VirtualComputeQuota
            }
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
    tags = ['virtualisedResourceQuota']
    operationId = "queryComputeQuota"

    def get(self):
        config = flask.current_app.osloconfig
        keystone = OpenStackClients(config).keystone()
        nova = OpenStackClients(config).nova()

        project_ids = [p.id for p in keystone.projects.list()]

        quotas_projects = []
        for project_id in project_ids:
            quotas = nova.quotas.get(project_id)

            quotas_projects.append({
                'resourceGroupId': quotas.id,
                'numVCPUs': quotas.cores,
                'numVcInstances': quotas.instances,
                'virtualMemSize': quotas.ram
            })

        return flask.jsonify(quotas_projects), OK


blueprint.add_url_rule(
    '/quotas/compute_resources', strict_slashes=False,
    view_func=ComputeResourceQuotaQueryAPI.as_view(
        'queryComputeQuota'),
    methods=['GET']
)

blueprint.add_url_rule(
    '/quotas/compute_resources', strict_slashes=False,
    view_func=ComputeResourceQuotaCreateAPI.as_view(
        'createNetworkQuota'),
    methods=['POST']
)

blueprint.add_url_rule(
    '/quotas/compute_resources', strict_slashes=False,
    view_func=ComputeResourceQuotaTerminateAPI.as_view(
        'terminateNetworkQuota'),
    methods=['DELETE']
)
