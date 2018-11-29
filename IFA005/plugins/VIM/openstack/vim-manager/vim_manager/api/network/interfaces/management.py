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

from vim_manager.api.network.schema import NetworkSubnet
from vim_manager.api.network.schema import NetworkSubnetData
from vim_manager.api.network.schema import VirtualNetwork
from vim_manager.api.network.schema import VirtualNetworkData
from vim_manager.api.network.schema import VirtualNetworkPort
from vim_manager.api.network.schema import VirtualNetworkPortData
from vim_manager.api.schema import AffinityOrAntiAffinityConstraint
from vim_manager.api.schema import Filter
from vim_manager.api.schema import Identifier
from vim_manager.api.schema import KeyValuePair


OK = http.HTTPStatus.OK.value
CREATED = http.HTTPStatus.CREATED.value
UNAUTHORIZED = http.HTTPStatus.UNAUTHORIZED.value
FORBIDDEN = http.HTTPStatus.FORBIDDEN.value
NOT_FOUND = http.HTTPStatus.NOT_FOUND.value
BAD_REQUEST = http.HTTPStatus.BAD_REQUEST.value
CONFLICT = http.HTTPStatus.CONFLICT.value
INTERNAL_SERVER_ERROR = http.HTTPStatus.INTERNAL_SERVER_ERROR.value

blueprint = flask.Blueprint('network_management', __name__)


class NetworkResource(object):

    def __init__(self, data, resource_type, neutron_client):
        self.data = data
        self.neutron = neutron_client
        self.resource_type = resource_type

    def is_dhcp_enabled(self):
        return True

    def segment_id(self):
        if self.data.get('provider:network_type') == 'gre':
            return self.data['provider:segmentation_id']
        elif self.data.get('provider:network_type') == 'vlan':
            return self.data['provider:segmentation_id']
        else:
            return "0"

    def network_data(self):
        resource_id = self.data['id']
        state = 'enabled' if self.data['status'] == 'ACTIVE' else 'disabled'
        return {'networkResourceId': resource_id,
                'networkResourceName': self.data['name'],
                'subnet': self.subnets_data(),
                'networkPort': self.network_port_data(),
                'bandwidth': 0,
                'networkType': self.data.get('provider:network_type'),
                'segmentType': self.segment_id(),
                'networkQoS': [],
                'isShared': self.data['shared'],
                'sharingCriteria': '',
                'zoneId': '',
                'operationalState': state,
                'metadata': self.data}

    def network_port_data(self):
        ports = self.neutron.list_ports(network_id=self.data['id'])['ports']
        state = 'enabled' if self.data['status'] == 'ACTIVE' else 'disabled'
        return [{'resourceId': port['id'],
                 'networkId': port['network_id'],
                 'attachedResourceId': port['device_id'],
                 'portType': '',
                 'segmentId': self.segment_id(),
                 'bandwidth': 0,
                 'operationalState': state,
                 'metadata': port} for port in ports]

    def subnet_data(self, subnet_id):
        subnet = self.neutron.show_subnet(subnet_id)['subnet']
        return {'resourceId': subnet['id'],
                'networkId': subnet['network_id'],
                'ipVersion': subnet['ip_version'],
                'gatewayIp': subnet['gateway_ip'],
                'cidr': subnet['cidr'],
                'isDhcpEnabled': subnet['enable_dhcp'],
                'addressPool': subnet.get('allocation_pools', None),
                'operationalState': 'enabled',
                'metadata': subnet}

    def subnets_data(self):
        subnet_ids = self.data.get('subnets', [])
        return [self.subnet_data(subnet_id) for subnet_id in subnet_ids]

    def export(self):
        if self.resource_type == 'network':
            return {'networkData': self.network_data(),
                    'subnetData': None,
                    'networkPortData': None}
        elif self.resource_type == 'subnet':
            return {'networkData': None,
                    'subnetData': self.subnet_data(self.data['id']),
                    'networkPortData': None}
        elif self.resource_type == 'port':
            return {'networkData': None,
                    'subnetData': None,
                    'networkPortData': self.network_port_data()}


class AllocateNetworkRequest(Schema):
    networkResourceName = fields.Str(
        required=True,
        description='Name provided by the consumer for the virtualised '
        'network resource to allocate. It can be used for '
        'identifying resources from consumer side.')
    reservationId = fields.Str(
        required=True,
        description='Identifier of the resource reservation applicable to '
        'this virtualised resource management operation.')
    networkResourceType = fields.Str(
        required=True,
        description='Type of virtualised network resource. Possible values '
        'are: "network", "subnet" or network-port.')
    typeNetworkData = fields.Str(
        VirtualNetworkData,
        required=True,
        description='The network data provides information about the '
        'particular virtual network resource to create. '
        'Cardinality can be "0" depending on the value of '
                    'networkResourceType.',
        many=True)
    typeNetworkPortData = fields.Str(
        VirtualNetworkPortData,
        required=True,
        description='The binary software image file.')
    typeSubnetData = fields.Str(
        NetworkSubnetData,
        required=True,
        description='The binary software image file.')
    affinityOrAntiAffinityConstraints = fields.Str(
        AffinityOrAntiAffinityConstraint,
        many=True,
        required=True,
        description='The binary software image file.')
    metadata = fields.Nested(
        KeyValuePair,
        many=True,
        required=True,
        description='The binary software image file.')
    resourceGroupId = fields.Str(
        required=True,
        description='Unique identifier of the "infrastructure resource '
        'group", logical grouping of virtual resources assigned '
        'to a tenant within an Infrastructure Domain.')
    locationConstraints = fields.Str(
        required=True,
        description='Controls the visibility of the image. In case of '
        '"private" value the image is available only for the '
        'tenant assigned to the provided resourceGroupId and the '
                    'administrator tenants of the VIM while in case of '
                    '"public" value, all tenants of the VIM can use the '
                    'image.')


class AllocateNetworkResult(Schema):
    networkData = fields.Nested(
        VirtualNetwork,
        required=True,
        description='If network types are created satisfactorily, it contains '
                    'the data relative to the instantiated virtualised '
                    'network resource. Cardinality can be "0" if the request '
                    'did not include creation of such type of resource.')
    subnetData = fields.Nested(
        NetworkSubnet,
        required=True,
        description='If subnet types are created satisfactorily, it contains '
        'the data relative to the allocated subnet. Cardinality can be "0" if '
        'the request did not include creation of such type of resource.')
    networkPortData = fields.Nested(
        VirtualNetworkPort,
        required=True,
        description='If network types are created satisfactorily, it contains '
                    'the data relative to the instantiated virtualised '
                    'network resource. Cardinality can be "0" if the request '
                    'did not include creation of such type of resource.')


class VirtualisedNetworkAllocateAPI(SwaggerView):
    """Allocate Virtualised Network Resource operation.

    This operation allows an authorized consumer functional block to request
    the allocation of virtualised network resources as indicated by the
    consumer functional block.
    """

    parameters = [
        {
            "name": "params",
            "in": "body",
            "schema": AllocateNetworkRequest,
            "required": True
        }
    ]
    responses = {
        CREATED: {
            'description': 'Identifier of the created Compute Flavour.',
            'schema': AllocateNetworkResult
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
            "description": "network already added",
        },
    }

    tags = ['virtualisedNetworkResources']
    operationId = "allocateNetwork"

    def post(self):
        data = flask.request.get_json()

        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        name = data['networkResourceName']
        # id = data['reservationId']
        if data['networkResourceType'] == 'network':
            # network_data = data.get('typeNetworkData', None)
            network = {
                'name': name,
                'admin_state_up': True
            }
            network = neutron.create_network({'network': network})
            data = NetworkResource(
                network['network'], 'network', neutron).export()
        elif data['networkResourceType'] == 'subnet':
            subnet_data = data.get('typeSubnetData', None)
            ip_versions = {
                'IPv4': 4,
                'IPv6': 6
            }
            body_create_subnet = {
                'subnet': {
                    'name': name,
                    'enable_dhcp': subnet_data['isDhcpEnabled'],
                    'network_id': subnet_data['networkId'],
                    # 'segment_id': None,
                    # 'project_id': '4fd44f30292945e481c7b8a0c8908869',
                    # 'tenant_id': '4fd44f30292945e481c7b8a0c8908869',
                    # 'dns_nameservers': [],
                    # 'allocation_pools': [
                    #     {
                    #         'start': '192.168.199.2',
                    #         'end': '192.168.199.254'
                    #         }
                    #     ],
                    'host_routes': [],
                    'ip_version': ip_versions[subnet_data['ipVersion']],
                    'gateway_ip': subnet_data['gatewayIp'],
                    'cidr': subnet_data['cidr'],
                    # 'id': '3b80198d-4f7b-4f77-9ef5-774d54e17126',
                    # 'created_at': '2016-10-10T14:35:47Z',
                    'description': '',
                    # 'ipv6_address_mode': None,
                    # 'ipv6_ra_mode': None,
                    # 'revision_number': 1,
                    # 'service_types': [],
                    'subnetpool_id': None,
                    # 'tags': ['tag1,tag2'],
                    # 'updated_at': '2016-10-10T14:35:47Z'
                }
            }

            subnet = neutron.create_subnet(body=body_create_subnet)
            data = NetworkResource(
                subnet['subnet'], 'subnet', neutron).export()
        elif data['networkResourceType'] == 'network-port':
            port_data = data.get('typeNetworkPortData', None)

            body_create_port = {
                "port": {
                    'name': name,
                    "admin_state_up": True,
                    "network_id": port_data['networkId'],
                }
            }

            port = neutron.create_port(body=body_create_port)
            data = NetworkResource(
                port['port'], 'port', neutron).export()

        return flask.jsonify(data), CREATED


class VirtualisedNetworkTerminateAPI(SwaggerView):
    """Terminate Virtualised Network Resource operation.

    This operation allows de-allocating and terminating one or more
    instantiated virtualised network resource(s). When the operation is done
    on multiple ids, it is assumed to be best-effort, i.e. it can succeed for
    a subset of the ids, and fail for the remaining ones.
    """

    parameters = [
        {
            "name": "networkResourceId",
            "in": "query",
            'type': 'array',
            'items': {'type': Identifier},
            "description": "Identifier of the virtualised network resource(s) "
                           "to be terminated.",
            "required": True
        }
    ]

    responses = {
        OK: {
            'description': 'Identifier(s) of the virtualised network '
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
    tags = ['virtualisedNetworkResources']
    operationId = "terminateNetworks"

    def delete(self):
        ids = flask.request.args.getlist('networkResourceId')
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        deleted_ids = []
        for id in ids:
            if neutron.list_networks(id=id)['networks']:
                try:
                    neutron.delete_network(id)
                    deleted_ids.append(id)
                except Exception:
                    pass
            if neutron.list_subnets(id=id)['subnets']:
                try:
                    neutron.delete_subnet(id)
                    deleted_ids.append(id)
                except Exception:
                    pass
            if neutron.list_ports(id=id)['ports']:
                try:
                    neutron.delete_port(id)
                    deleted_ids.append(id)
                except Exception:
                    pass

        return flask.jsonify(deleted_ids), OK


class VirtualisedNetworkQueryAPI(SwaggerView):
    """Query Virtualised Network Resource operation.

    This operation allows querying information about instantiated virtualised
    network resources.
    """

    parameters = [
        {
            "name": "networkQueryFilter",
            "in": "body",
            "schema": Filter,
            "description": "Query filter based on e.g. name, identifier, "
                           "meta-data information or status information, "
                           "expressing the type of information to be "
                           "retrieved. It can also be used to specify one or "
                           "more resources to be queried by providing their "
                           "identifiers.",
            "required": True
        }
    ]
    responses = {
        OK: {
            'description': 'Element containing information about the virtual '
                           'network resource(s) matching the filter. The '
                           'cardinality can be 0 if no matching network '
                           'resources exist.',
            'schema': {
                'type': 'array',
                'items': VirtualNetwork
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
    tags = ['virtualisedNetworkResources']
    operationId = "queryNetworks"

    def get(self):
        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        networks = neutron.list_networks()['networks']

        resources = [NetworkResource(
            network, 'network', neutron).export()['networkData']
            for network in networks]

        return flask.jsonify(resources), OK


blueprint.add_url_rule(
    '/network_resources', strict_slashes=False,
    view_func=VirtualisedNetworkQueryAPI.as_view(
        'queryNetwork'),
    methods=['GET']
)

blueprint.add_url_rule(
    '/network_resources', strict_slashes=False,
    view_func=VirtualisedNetworkAllocateAPI.as_view(
        'createNetwork'),
    methods=['POST']
)

blueprint.add_url_rule(
    '/network_resources', strict_slashes=False,
    view_func=VirtualisedNetworkTerminateAPI.as_view(
        'deleteNetwork'),
    methods=['DELETE']
)
