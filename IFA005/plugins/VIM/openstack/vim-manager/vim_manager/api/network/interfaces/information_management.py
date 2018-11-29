# Copyright 2018 b<>com. All rights reserved.
# This software is the confidential intellectual property of b<>com. You shall
# not disclose it and shall use it only in accordance with the terms of the
# license agreement you entered into with b<>com.
# IDDN number:
#

import flask
import http

from flasgger import SwaggerView

from vim_manager.api.network.schema import VirtualNetworkResourceInformation
from vim_manager.api.schema import Filter

OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('network_information_management', __name__)


class InformationQueryAPI(SwaggerView):
    """Query Virtualised Network Resource Information operation.

    This operation supports retrieval of information for the various types of
    virtualised network resources managed by the VIM.
    """

    parameters = [
        {
            "name": "informationQueryFilter",
            "in": "body",
            "schema": Filter,
            "description": "Filter defining the information of consumable "
                           "virtualised resources on which the query applies.",
            "required": True
        }
    ]
    responses = {
        OK: {
            'description': 'Virtualised network resource information in the '
                           'VIM that satisfies the query condition.',
            'schema': {
                'type': 'array',
                'items': VirtualNetworkResourceInformation
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
    tags = ['virtualisedNetworkResourcesInformation']
    operationId = "queryNetworkInformation"

    def get(self):

        return flask.jsonify(''), OK


blueprint.add_url_rule(
    '/information', strict_slashes=False,
    view_func=InformationQueryAPI.as_view(
        'queryNetworkInformation'),
    methods=['GET']
)
