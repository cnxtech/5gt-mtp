# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#

import flask
import http

from vim_manager.osc.clients import OpenStackClients

from flasgger import SwaggerView

from vim_manager.api.compute.schema import VirtualComputeFlavour


OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('flavour_resources', __name__)


def extract_flavour(flavour):
    return {
        'flavourId': flavour.id,
        'accelerationCapability': '',
        'virtualMemory': {
            'virtualMemSize': flavour.ram,
            'virtualMemOversubscriptionPolicy': None,
            'numaEnabled': True,
        },
        'virtualCpu': {
            'cpuArchitecture': '',
            'numVirtualCpu': flavour.vcpus,
            'cpuClock': '',
            'virtualCpuOversubscriptionPolicy': '',
            'virtualCpuPinning': {
            }
        },
        'storageAttributes': {
            'storageId': '',
            'storageName': '',
            'flavourId': '',
            'typeOfStorage': '',
            'sizeOfStorage': flavour.disk,
            'ownerId': '',
            'zoneId': '',
            'hostId': '',
            'operationalState': '',
        },
        'virtualNetworkInterface': {},
    }


class ComputeFlavourCreateAPI(SwaggerView):
    """Create Compute Flavour operation.

    This operation allows requesting the creation of a flavour as indicated
    by the consumer functional block.
    """

    parameters = [
        {
            "name": "Flavour",
            "in": "body",
            "schema": VirtualComputeFlavour,
            "required": True
        }
    ]
    responses = {
        CREATED: {
            'description': 'Identifier of the created Compute Flavour.',
            'schema': {
                'type': 'string'
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
        CONFLICT: {
            "description": "flavour already added",
        },
    }

    tags = ['virtualisedComputeResources']
    operationId = "createFlavour"

    def post(self):
        data = flask.request.get_json()

        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        id = data['flavourId']
        ram = data['virtualMemory']['virtualMemSize']
        vcpus = data['virtualCpu']['numVirtualCpu']
        disk = data['storageAttributes']['sizeOfStorage']

        flavour = nova.flavors.create(id, ram, vcpus, disk, id)

        return flask.jsonify(flavour.id), CREATED


class ComputeFlavourDeleteAPI(SwaggerView):
    """Delete Compute Flavour operation.

    This operation allows deleting a Compute Flavour.
    """

    parameters = [
        {
            "name": "id",
            "in": "path",
            "type": "string",
            "description": "Identifier of the Compute Flavour to be deleted.",
            "required": True
        }
    ]

    responses = {
        OK: {
            'description': 'No output parameters',
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
    # definitions = dict(SoftwareImage=SoftwareImageInformation)
    tags = ['virtualisedComputeResources']
    operationId = "deleteFlavours"

    def delete(self, id):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        nova.flavors.delete(id)

        return '', OK


class ComputeFlavourQueryAPI(SwaggerView):
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
                'items': VirtualComputeFlavour
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
    operationId = "queryFlavors"

    def get(self):
        config = flask.current_app.osloconfig
        nova = OpenStackClients(config).nova()

        flavours = [extract_flavour(f) for f in nova.flavors.list()]

        return flask.jsonify(flavours), OK


blueprint.add_url_rule(
    '/compute_resources/flavours', strict_slashes=False,
    view_func=ComputeFlavourQueryAPI.as_view(
        'queryFlavour'),
    methods=['GET']
)


blueprint.add_url_rule(
    '/compute_resources/flavours', strict_slashes=False,
    view_func=ComputeFlavourCreateAPI.as_view(
        'createFlavour'),
    methods=['POST']
)

blueprint.add_url_rule(
    '/compute_resources/flavours/<id>', strict_slashes=False,
    view_func=ComputeFlavourDeleteAPI.as_view(
        'deleteFlavour'),
    methods=['DELETE']
)
