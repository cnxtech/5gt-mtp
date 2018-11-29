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

from vim_manager.api.resource_quota.schema import ResourceGroupIds
from vim_manager.api.resource_quota.schema import VirtualNetworkQuota
from vim_manager.api.resource_quota.schema import VirtualNetworkQuotaData
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

blueprint = flask.Blueprint('network_resources_quota', __name__)


class CreateNetworkResourceQuotaRequest(Schema):
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
                    'group", logical grouping of virtual resources assigned '
                    'to a tenant within an Infrastructure Domain.')
    virtualComputeQuota = fields.Nested(
        VirtualNetworkQuotaData,
        required=True,
        description='Type and configuration of virtualised network resources '
                    'that need to be restricted by the quota, e.g. '
                    '{"numPublicIps": 20}.')


class NetworkResourceQuotaCreateAPI(SwaggerView):
    """Create Network Resource Quota operation.

    This operation allows requesting the quota of virtualised network
    resources as indicated by the consumer functional block.
    """

    parameters = [
        {
            "name": "body",
            "in": "body",
            "schema": CreateNetworkResourceQuotaRequest,
            "required": True
        }
    ]
    responses = {
        CREATED: {
            'description': 'Element containing information about the quota '
                           'resource.',
            'schema': VirtualNetworkQuota,
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
            "description": "",
        },
    }

    tags = ['virtualisedResourceQuota']
    operationId = "createNetworkQuota"

    def post(self):
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        data = flask.request.get_json()

        resource_group_id = data['resourceGroupId']
        network_quota = data['virtualNetworkQuota']

        resources = {'quota': {}}
        if network_quota.get('numPublicIps'):
            resources['quota']['floatingip'] = network_quota.get(
                'numPublicIps')

        if network_quota.get('numPorts'):
            resources['quota']['port'] = network_quota.get('numPorts')

        if network_quota.get('numSubnets'):
            resources['quota']['subnet'] = network_quota.get('numSubnets')

        neutron.update_quota(resource_group_id, body=resources)

        quotas = neutron.show_quota(resource_group_id)['quota']
        quotas_data = {
            'resourceGroupId': resource_group_id,
            'numPublicIps': quotas['floatingip'],
            'numPorts': quotas['port'],
            'numSubnets': quotas['subnet'],
        }

        return flask.jsonify(quotas_data), CREATED


class NetworkResourceQuotaTerminateAPI(SwaggerView):
    """Terminate Network Resource Quota operation.

    This operation allows terminating one or more issued network resource
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
            'schema': ResourceGroupIds,
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
    operationId = "terminateNetworkQuota"

    def delete(self):
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        resource_group_ids = flask.request.args.getlist('resourceGroupId')

        deleted_ids = []
        for resource_group_id in resource_group_ids:
            try:
                neutron.delete_quota(resource_group_id)
                deleted_ids.append(resource_group_id)
            except Exception:
                pass

        return flask.jsonify(deleted_ids), OK


class NetworkResourceQuotaQueryAPI(SwaggerView):
    """Query Network Resource Quota operation.

    This operation allows querying information about quota network resources
    that the consumer has access to.
    """

    responses = {
        OK: {
            'description': 'Element containing information about the quota '
                           'resource(s) matching the filter. The cardinality '
                           'can be 0 if no matching quota exists.',
            'schema': {
                'type': 'array',
                'items': VirtualNetworkQuota
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
    operationId = "queryNetworkQuota"

    def get(self):
        config = flask.current_app.osloconfig
        keystone = OpenStackClients(config).keystone()
        neutron = OpenStackClients(config).neutron()

        project_ids = [p.id for p in keystone.projects.list()]

        quotas_projects = []
        for project_id in project_ids:
            quotas = neutron.show_quota(project_id)['quota']
            quotas_projects.append({
                'resourceGroupId': project_id,
                'numPublicIps': quotas['floatingip'],
                'numPorts': quotas['port'],
                'numSubnets': quotas['subnet'],
            })

        return flask.jsonify(quotas_projects), OK


blueprint.add_url_rule(
    '/quotas/network_resources', strict_slashes=False,
    view_func=NetworkResourceQuotaQueryAPI.as_view(
        'queryNetworkQuota'),
    methods=['GET']
)

blueprint.add_url_rule(
    '/quotas/network_resources', strict_slashes=False,
    view_func=NetworkResourceQuotaCreateAPI.as_view(
        'createNetworkQuota'),
    methods=['POST']
)

blueprint.add_url_rule(
    '/quotas/network_resources', strict_slashes=False,
    view_func=NetworkResourceQuotaTerminateAPI.as_view(
        'terminateNetworkQuota'),
    methods=['DELETE']
)
