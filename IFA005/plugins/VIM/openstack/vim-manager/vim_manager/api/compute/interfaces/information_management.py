# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#

import flask
import http

from flasgger import SwaggerView

from vim_manager.api.compute.schema import VirtualComputeResourceInformation

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('compute_information_management', __name__)


class InformationQueryAPI(SwaggerView):
    """Query Virtualised Compute Resource Information operation.

    This operation supports retrieval of information for the various types of
    virtualised compute resources managed by the VIM.
    """

    responses = {
        OK: {
            'description': 'Virtualised compute resource information in the '
                           'VIM that satisfies the query condition.',
            'schema': {
                'type': 'array',
                'items': VirtualComputeResourceInformation
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
    operationId = "queryComputeInformation"

    def get(self):

        virtual_memory = {
            'virtualMemSize': 0,
            'virtualMemOversubscriptionPolicy': '',
            'numaSupported': True
        }

        virtual_cpu = {
            'cpuArchitecture': '',
            'numVirtualCpu': 0,
            'cpuClock': 0,
            'virtualCpuOversubscriptionPolicy': '',
            'virtualCpuPinningSupported': True,
        }

        informations = {
            'computeResourceTypeId': '',
            'virtualMemory': virtual_memory,
            'virtualCPU': virtual_cpu,
            'accelerationCapability': '',
        }
        return flask.jsonify(informations), OK


blueprint.add_url_rule(
    '/compute_resources/information', strict_slashes=False,
    view_func=InformationQueryAPI.as_view(
        'queryComputeInformation'),
    methods=['GET']
)
