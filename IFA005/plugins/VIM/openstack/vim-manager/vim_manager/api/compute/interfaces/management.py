# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#

import flask
import http

from vim_manager.osc.clients import OpenStackClients

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api.compute.schema import VirtualCompute
from vim_manager.api.compute.schema import VirtualInterfaceData

from vim_manager.api.schema import AffinityOrAntiAffinityConstraint
from vim_manager.api.schema import Identifier
from vim_manager.api.schema import UserData

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_management', __name__)


def extract_virtual_compute(server):
    return {'computeId': server.id,
            'computeName': server.name,
            'flavourId': server.flavor['id'],
            'accelerationCapability': '',
            'virtualCpu': extract_virtual_cpu(server),
            'virtualMemory': extract_virtual_memory(server),
            'virtualNetworkInterface': [],
            'virtualDisks': '',
            'vcImageId': '',
            'zoneId': getattr(server, 'OS-EXT-AZ:availability_zone'),
            'hostId': server.hostId,
            'operationalState':
                'enabled' if server.status == 'ACTIVE' else 'disabled'}


def extract_virtual_cpu(server):
    return {'cpuArchitecture': '',
            'numVirtualCpu': '',
            'cpuClock': '',
            'virtualCpuOversubscriptionPolicy': '',
            'virtualCpuPinning': extract_virtual_cpu_pinning(server)}


def extract_virtual_cpu_pinning(server):
    policy = 'dynamic' if True else 'static'
    return {'cpuPinningPolicy': policy,
            'cpuPinningRules': '',
            'cpuMap': ''}


def extract_virtual_memory(server):
    return {'virtualMemSize': '',
            'virtualMemOversubscriptionPolicy': '',
            'numaEnabled': ''}


class AllocateComputeRequest(Schema):
    computeName = fields.Str(
        required=True,
        description='Name provided by the consumer for the virtualised '
                    'compute resource to allocate. It can be used for '
                    'identifying resources from consumer side.')
    reservationId = fields.Str(
        required=True,
        description='Identifier of the resource reservation applicable to '
                    'this virtualised resource management operation. '
                    'Cardinality can be 0 if no resource reservation was '
                    'used.')
    computeFlavourId = fields.Str(
        required=True,
        description='Identifier of the Compute Flavour that provides '
                    'information about the particular memory, CPU and disk '
                    'resources for virtualised compute resource to allocate. '
                    'The Compute Flavour is created with Create Compute '
                    'Flavour operation')
    affinityOrAntiAffinityConstraints = fields.Nested(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='A list of elements with affinity or anti affinity '
                    'information of the virtualised compute resource to '
                    'allocate. There should be a relation between the '
                    'constraints defined in the different elements of the '
                    'list.')
    interfaceData = fields.Nested(
        VirtualInterfaceData,
        many=True,
        required=True,
        description='The data of network interfaces which are specific to a '
                    'Virtual Compute Resource instance.')
    vcImageId = fields.Str(
        required=True,
        description='Identifier of the virtualisation container software '
                    'image (e.g. a virtual machine image). Cardinality can be '
                    '0 if an "empty" virtualisation container is allocated. ')
    metadata = fields.Dict(
        required=True,
        description='List of metadata key-value pairs used by the consumer '
                    'to associate meaningful metadata to the related '
                    'virtualised resource.')
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
                    'group", logical grouping of virtual resources assigned '
                    'to a tenant within an Infrastructure Domain.')
    locationConstraints = fields.Str(
        required=True,
        description='If present, it defines location constraints for the '
                    'resource(s) is (are) requested to be allocated, e.g. in '
                    'what particular Resource Zone.')
    userData = fields.Nested(
        UserData,
        required=True,
        description='Element containing user data to customize the '
                    'virtualised compute resource at boot-time.')


class VirtualisedComputeResourceAllocateAPI(SwaggerView):
    """Allocate Virtualised Compute Resource operation.

    This operation allows requesting the allocation of virtualised compute
    resources as indicated by the consumer functional block.
    """

    parameters = [
        {
            "name": "body",
            "in": "body",
            "schema": AllocateComputeRequest,
            "required": True
        }
    ]
    responses = {
        CREATED: {
            'description': 'Element containing information of the newly '
                           'instantiated virtualised compute resource.',
            'schema': VirtualCompute,
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
        CONFLICT: {
            "description": "software image already added",
        },
    }

    tags = ['virtualisedComputeResources']
    operationId = "allocateCompute"

    def post(self):
        data = flask.request.get_json()

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        name = data['computeName']
        image = data['vcImageId']
        flavour = data['computeFlavourId']

        kwargs = dict(
            meta=None,
            files={},
            reservation_id=None,
            min_count=1,
            max_count=1,
            security_groups=[],
            userdata=None,
            key_name=None,
            availability_zone=None,
            block_device_mapping_v2=[],
            nics=[{'net-id': data.get('networkId')}],
            scheduler_hints={},
            config_drive=None,
        )

        server = nova.servers.create(name, image, flavour, **kwargs)
        data = extract_virtual_compute(server)

        return flask.jsonify(data), CREATED


class VirtualisedComputeResourceTerminateAPI(SwaggerView):
    """Terminate Virtualised Compute Resource operation.

    This operation allows de-allocating and terminating one or more
    instantiated virtualised compute resource(s). When the operation is done
    on multiple resources, it is assumed to be best-effort, i.e. it can
    succeed for a subset of the resources, and fail for the remaining ones.
    """

    parameters = [
        {
            'name': 'computeId',
            'in': 'query',
            'type': 'array',
            'items': {'type': Identifier},
            'description': 'Identifier(s) of the virtualised compute '
                           'resource(s) to be terminated.',
            'required': True
        }
    ]

    responses = {
        OK: {
            'description': 'Identifier(s) of the virtualised compute '
                           'resource(s) successfully terminated.',
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

    tags = ['virtualisedComputeResources']
    operationId = "terminateResources"

    def delete(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        compute_ids = flask.request.args.getlist('computeId')

        stopped_servers = []
        for compute_id in compute_ids:
            try:
                server = nova.servers.get(compute_id)
                server.delete()
                stopped_servers.append(compute_id)
            except Exception:
                pass

        return flask.jsonify(stopped_servers), OK


class VirtualisedComputeResourceQueryAPI(SwaggerView):
    """Query Virtualised Compute Resource operation.

    This operation allows querying information about instantiated virtualised
    compute resources.
    """
    responses = {
        OK: {
            'description': 'Element containing information about the virtual '
                           'compute resource(s) matching the filter. The '
                           'cardinality can be 0 if no matching compute '
                           'resources exist.',
            'schema': {
                'type': 'array',
                'items': VirtualCompute
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
    tags = ['virtualisedComputeResources']
    operationId = "queryResources"

    def get(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        servers = nova.servers.list()

        resources = [extract_virtual_compute(server) for server in servers]

        return flask.jsonify(resources), OK


blueprint.add_url_rule(
    '/compute_resources', strict_slashes=False,
    view_func=VirtualisedComputeResourceQueryAPI.as_view(
        'queryCompute'),
    methods=['GET']
)


blueprint.add_url_rule(
    '/compute_resources', strict_slashes=False,
    view_func=VirtualisedComputeResourceAllocateAPI.as_view(
        'allocateCompute'),
    methods=['POST']
)

blueprint.add_url_rule(
    '/compute_resources', strict_slashes=False,
    view_func=VirtualisedComputeResourceTerminateAPI.as_view(
        'TerminateCompute'),
    methods=['DELETE']
)
