# Copyright 2018 b<>com.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.
# IDDN number: IDDN.FR.001.470053.000.S.C.2018.000.00000.
#

import flask
import http
import sys
import time
from vim_manager.osc.clients import OpenStackClients

from flasgger import fields
from flasgger import Schema
from flasgger import SwaggerView

from vim_manager.api import common
from vim_manager import conf
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

def subnet_has_gateway_interface(neutron, subnet_id):

    # List all the port for this project
    port_list = neutron.list_ports(project_id=conf.cfg.CONF.DEFAULT.project_id)

    # Check among them if there is a port on this subnet whitch has the address ending by 1 which means that
    # this is a gateway interface, thus it is attached to the router and it is an admin subnet.
    for port in  port_list['ports']:
        # TODO check if using [0] could be a problem, the first interface should be the one attached to the router
        if port['fixed_ips'][0]['subnet_id'] == subnet_id:
            tab_address = port['fixed_ips'][0]['ip_address'].split('.')
            if tab_address[3] == '1':
                return True
    # import ipdb; ipdb.set_trace()
    return False


def generate_cloudinit_string(vlan_id, floating_ip, internal_ipaddr):
    return (
        '#cloud-config\n'
        + 'users:\n'
        + '  - default\n'
        + '\n'
        + 'write_files:\n'
        + '  - path: /etc/network/interfaces.d/50-cloud-init.cfg\n'
        + '    content: |\n'
        + '      auto lo\n'
        + '      iface lo inet loopback\n'
        + '\n'
        + '      auto ens3\n'
        + '      iface ens3 inet dhcp\n'
        + '\n'
        + '      auto ens4\n'
        + '      iface ens4 inet manual\n'
        + '\n'
        + '      auto vxlan' + vlan_id + '\n'
        + '      iface vxlan' + vlan_id + ' inet manual\n'
        + '        pre-up ip link add vxlan' + vlan_id + ' type vxlan id ' + vlan_id + ' dev ens3 dstport 0 || true\n'
        + '        pre-up bridge fdb append to 00:00:00:00:00:00 dst ' + floating_ip + ' dev vxlan' + vlan_id + ' || true\n'
        + '        up ip link set \\$IFACE up || true\n'
        + '        down ip link set \\$IFACE down || true\n'
        + '        post-down ip link del vxlan' + vlan_id + ' || true\n'
        + '\n'
        + '      auto br_vtep\n'
        + '      iface br_vtep inet static\n'
        + '        address ' + internal_ipaddr + '/24\n'
        + '        bridge_ports ens4' + ' vxlan' + vlan_id + '\n'
        + '        up ip link set br_vtep up\n'
        + '\n'
        + 'packages:\n'
        + '  - bridge-utils\n'
        + '\n'
        + 'runcmd:\n'
        + '- sudo systemctl restart networking.service\n'
        + '\n'
        + '- sudo reboot\n'
    )

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
        return {
            'networkResourceId': resource_id,
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
            'metadata': self.data
        }

    def network_port_data(self):
        ports = self.neutron.list_ports(network_id=self.data['id'])['ports']
        state = 'enabled' if self.data['status'] == 'ACTIVE' else 'disabled'
        return [{
            'resourceId': port['id'],
            'networkId': port['network_id'],
            'attachedResourceId': port['device_id'],
            'portType': '',
            'segmentId': self.segment_id(),
            'bandwidth': 0,
            'operationalState': state,
            'metadata': port
        } for port in ports]

    def subnet_data(self, subnet_id):
        subnet = self.neutron.show_subnet(subnet_id)['subnet']
        return {
            'resourceId': subnet['id'],
            'networkId': subnet['network_id'],
            'ipVersion': subnet['ip_version'],
            'gatewayIp': subnet['gateway_ip'],
            'cidr': subnet['cidr'],
            'isDhcpEnabled': subnet['enable_dhcp'],
            'addressPool': subnet.get('allocation_pools', None),
            'operationalState': 'enabled',
            'metadata': subnet
        }

    def subnets_data(self):
        subnet_ids = self.data.get('subnets', [])
        return [self.subnet_data(subnet_id) for subnet_id in subnet_ids]

    def export(self):
        if self.resource_type == 'network':
            return {
                'networkData': self.network_data(),
                'subnetData': None,
                'networkPortData': None
            }
        elif self.resource_type == 'subnet':
            return {
                'networkData': None,
                'subnetData': self.subnet_data(self.data['id']),
                'networkPortData': None
            }
        elif self.resource_type == 'port':
            return {
                'networkData': None,
                'subnetData': None,
                'networkPortData': self.network_port_data()
            }
        elif self.resource_type == 'float':
            return {
                'networkData': None,
                'subnetData': None,
                'networkPortData': {
                    'metadata': { 'floating_ip' : self.data['floating_ip_address'] }
                }
            }


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

    parameters = [{
        "name": "params",
        "in": "body",
        "schema": AllocateNetworkRequest,
        "required": True
    }]
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
            network = {'name': name, 'admin_state_up': True}
            network = neutron.create_network({'network': network})
            data = NetworkResource(network['network'], 'network',
                                   neutron).export()
        elif data['networkResourceType'] == 'subnet':
            subnet_data = data.get('typeSubnetData', None)
            if 'metadata' in subnet_data:
                if 'dns' in subnet_data['metadata']:
                    meta_dns = subnet_data['metadata']['dns']
                elif 'subnet_type' in subnet_data['metadata'] and subnet_data['metadata']['subnet_type'] == 'admin':
                    meta_dns = conf.cfg.CONF.vtep.vtep_dns
                else: # we are not supposed to reach this case, but just in case
                    meta_dns = []
            else:
                meta_dns = []

            ip_versions = {'IPv4': 4, 'IPv6': 6}
            body_create_subnet = {
                'subnet': {
                    'name': name,
                    'enable_dhcp': subnet_data['isDhcpEnabled'],
                    'network_id': subnet_data['networkId'],
                    # 'segment_id': None,
                    # 'project_id': '4fd44f30292945e481c7b8a0c8908869',
                    # 'tenant_id': '4fd44f30292945e481c7b8a0c8908869',
                    'dns_nameservers': meta_dns,
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
            data = NetworkResource(subnet['subnet'], 'subnet',
                                   neutron).export()
        elif data['networkResourceType'] == 'network-port':
            port_data = data.get('typeNetworkPortData', None)

            if 'metadata' in port_data: # floating_ip or vtep
                if port_data['metadata']['type'] == "floating_ip":
                    body_create_floatingip = {
                        "floatingip": {
                        "floating_network_id": port_data['networkId'] # external network
                        }
                    }
                    # request a floatingip for this project (on the network external)
                    # this address won't be bind to any interface, it's done when the vm is created
                    floating = neutron.create_floatingip(body=body_create_floatingip)
                    data = NetworkResource(floating['floatingip'], 'float', neutron).export()

                    # add the subnet of the admin network to the project's router
                    body_add_inerface_router = {
                        "subnet_id": port_data['metadata']['subnet_id']
                    }

                    # If there is no gateway interface for this subnet, it means that we need to add
                    # this subnet to the router
                    if not subnet_has_gateway_interface(neutron, port_data['metadata']['subnet_id']):
                        # If there is more than one router for a project, we can ad the name= of the router
                        # in the config file
                        router = neutron.list_routers(project_id=conf.cfg.CONF.DEFAULT.project_id)
                        # TODO store the router_id in the config file ?
                        router_id = router['routers'][0]['id']
                        neutron.add_interface_router(router_id, body_add_inerface_router)

                elif port_data['metadata']['type'] == "vtep":
                    port_data = data.get('typeNetworkPortData', None)
                    nova = OpenStackClients(config).nova()

                    vtep_name = conf.cfg.CONF.vtep.vtep_name
                    vtep_image =  conf.cfg.CONF.vtep.vtep_image
                    vtep_flavor = conf.cfg.CONF.vtep.vtep_flavor

                    admin_network_id = port_data['metadata']['admin_interface']['network_id']
                    internal_ipaddr = port_data['metadata']['internal_interface']['fixed_ip']
                    internal_network_id = port_data['metadata']['internal_interface']['network_id']

                    # add the network interfaces
                    _nics=[]
                    _nics.append( { 'net-id' : admin_network_id } )
                    _nics.append( { 'net-id' : internal_network_id, 'v4-fixed-ip' : internal_ipaddr } )

                    # the connection to the vm is done with ssh key and user ubuntu
                    # ssh -i vim-manager-key ubuntu@floating_ip
                    key_name = 'vim-manager-key'

                    # param for cloudinit user_data
                    vlan_id= port_data['segmentId']
                    remote_floating_ip = port_data['metadata']['remote_floating_ip']

                    kwargs = dict(
                    meta=None,
                    files={},
                    reservation_id=None,
                    min_count=1,
                    max_count=1,
                    security_groups=[],
                    userdata=generate_cloudinit_string(vlan_id, remote_floating_ip, internal_ipaddr),
                    key_name=key_name,
                    availability_zone=None,
                    block_device_mapping_v2=[],
                    nics=_nics,
                    scheduler_hints={},
                    config_drive=None,
                    )
                    # create the vm
                    server = nova.servers.create(vtep_name, vtep_image, vtep_flavor, **kwargs)

                    # TODO not the best way, but we need to have the vm up and running to look for the port
                    max_retry = 0
                    while server.status == 'BUILD':
                        time.sleep( 6 ) # to avoid to access openstack to often
                        # the try is to avoid crash if the server doesn't yet exist just wait
                        try:
                            server = nova.servers.find(name=vtep_name)
                        except Exception:
                            pass
                        max_retry = max_retry + 1
                        if max_retry > 10:
                            break

                    # get the local admin ip address to bound the floating ip address
                    if data['networkResourceName'] in server.networks.keys():
                        # the local ip address is always index 0 hence the hardcoded value [0]
                        admin_ipaddr = server.networks[data['networkResourceName']][0]
                    else:
                        # TODO if we stop we probably need to delete the vtep vm because if we try another time
                        # it won't work as the vm will already exist so we have to think of a way to clean up if
                        # ther is a problem
                        return flask.jsonify('Error wrong networkResourceName, expecting: ' +  str(server.networks.keys())  ), OK

                    # get the port_id of the vm_vtp admin interface ip for floating ip mapping
                    ports = neutron.list_ports(network_id=admin_network_id)['ports']
                    for port in ports:
                        for fixed_ip in port['fixed_ips']:
                            if (fixed_ip['ip_address'] == admin_ipaddr):
                                port_id = port['id']
                                break

                    body_update_floatingip = {
                        "floatingip": {
                            "port_id": port_id
                        }
                    }
                    # get the id of the floating ip that we want to bind to the the vtep_vm
                    floatingip_id = neutron.list_floatingips(floating_ip_address=port_data['metadata']['local_floating_ip'])['floatingips'][0]['id']
                    # attach the floating ip to the interface of the vm
                    float_update = neutron.update_floatingip(floatingip_id, body=body_update_floatingip)

                    # disable port security for internal port (the MAC address of the the bridge interface
                    # br_vtp is not known by openstack so if the port security is true, the traffic is blocked)

                    # get the internal interface port id
                    ports = neutron.list_ports(network_id=internal_network_id)['ports']
                    for port in ports:
                        for fixed_ip in port['fixed_ips']:
                            if (fixed_ip['ip_address'] == internal_ipaddr):
                                port_id = port['id']
                                break

                    body_update_security_port = {
                        "port": {
                            "security_groups": [], # no security group
                            "port_security_enabled": False
                        }
                    }
                    # update port with security disable
                    port_update = neutron.update_port(port_id, body=body_update_security_port)
                    print (port_update)
                    # import ipdb; ipdb.set_trace()
                    data = NetworkResource(float_update['floatingip'], 'float', neutron).export()

            else: # regular port creation
                body_create_port = {
                    "port": {
                        'name': name,
                        "admin_state_up": True,
                        "network_id": port_data['networkId'],
                    }
                }
                port = neutron.create_port(body=body_create_port)
                data = NetworkResource(port['port'], 'port', neutron).export()

        return flask.jsonify(data), CREATED


class VirtualisedNetworkTerminateAPI(SwaggerView):
    """Terminate Virtualised Network Resource operation.

    This operation allows de-allocating and terminating one or more
    instantiated virtualised network resource(s). When the operation is done
    on multiple ids, it is assumed to be best-effort, i.e. it can succeed for
    a subset of the ids, and fail for the remaining ones.
    """

    parameters = [{
        "name": "networkResourceId",
        "in": "query",
        'type': 'array',
        'items': {
            'type': Identifier
        },
        "description": "Identifier of the virtualised network resource(s) "
                       "to be terminated.",
        "required": True
    }]

    responses = {
        OK: {
            'description': 'Identifier(s) of the virtualised network '
                           'resource(s) successfully terminated.',
            'schema': {
                'type': 'array',
                'items': {
                    'type': Identifier
                }
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
                    # import ipdb; ipdb.set_trace()
                    # -- Identifying an admin interface
                    # An admin interface is a special interface, when you delete it, you need to delete the vtep vm if there is
                    # one and release the floating ip used to access it

                    # Check if the subnet has a gateway interface, that means it's an admin subnet
                    if subnet_has_gateway_interface(neutron, id):
                        address = None
                        # check if there is a vtep vm to delete
                        nova = OpenStackClients(config).nova()
                        vtep_name = conf.cfg.CONF.vtep.vtep_name
                        server_list = nova.servers.findall()
                        for server in server_list:
                            if server.name == vtep_name:
                                for key, value in server.addresses.items():
                                    for item in value:
                                        if item['OS-EXT-IPS:type'] == 'floating':
                                            address = item['addr']
                                            # print ('---> found address: ', address)
                                            break

                                # delete server
                                server = nova.servers.delete(server.id)
                                # just to avoid exception check if we have found a floating address
                                if address != None:
                                    floatingip_id = neutron.list_floatingips(floating_ip_address=address)['floatingips'][0]['id']
                                    float_delete = neutron.delete_floatingip(floatingip_id)
                                    print(float_delete)
                                break

                        # TODO either I look for the router in the list or to improve speed, I put it in conf file
                        # sometime there is more than one router in the tenant so maybe it would be better to have
                        # the router_id in conf
                        router = neutron.list_routers(project_id=conf.cfg.CONF.DEFAULT.project_id)
                        router_id = router['routers'][0]['id']
                        # remove the admin subnet from router interfaces
                        body_remove_inerface_router = {
                            "subnet_id": id
                        }
                        neutron.remove_interface_router(router_id, body_remove_inerface_router)

                    # delete subnet
                    neutron.delete_subnet(id)
                    deleted_ids.append(id)
                except Exception as error_msg:
                    print ('-----> exception: ' + str(error_msg))
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

    parameters = [{
        "name": "queryNetworkFilter",
        "in": "body",
        "schema": Filter,
        "description": "Query filter based on e.g. name, identifier, "
                       "meta-data information or status information, "
                       "expressing the type of information to be "
                       "retrieved. It can also be used to specify one or "
                       "more resources to be queried by providing their "
                       "identifiers.",
        "required": True
    }]
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

    # network the structure to insepct
    # filter what to look for
    def test_value(self, filter, network):

        elt_list = filter['param'].split('.')
        size_list = len(elt_list)

        if ( size_list == 1):
            return common.compare_value(filter['op'], network[elt_list[0]], filter['value'])
        elif (size_list == 2) and (elt_list[0] == 'metadata'):
            return common.compare_value(filter['op'], network[elt_list[0]][elt_list[1]], filter['value'])
        elif (elt_list[0] == 'subnet'):
            # for the moment we only process subnet but not networkPort
            # null element are not managed
            for item in network['subnet']:
                if ( size_list == 2):
                    # import ipdb; ipdb.set_trace()
                    return common.compare_value(filter['op'], item[elt_list[1]], filter['value'])
                elif ( size_list == 3):
                    # import ipdb; ipdb.set_trace()
                    return common.compare_value(filter['op'], item[elt_list[1]][elt_list[2]], filter['value'])
                else:
                    return -1
        else:
            return '-1'

    def post(self):
        data_filter = flask.request.get_json()
        filter_list = list(data_filter.keys())

        config = flask.current_app.osloconfig
        neutron = OpenStackClients(config).neutron()

        networks = neutron.list_networks()['networks']

        resources = [
            NetworkResource(network, 'network',
                            neutron).export()['networkData']
            for network in networks
        ]

        #------------------------------------------------------
        # For debug
        # f = open( 'file.txt', 'w' )
        # f.write(resources)
        # f.close()

        # f = open("file.txt").read()
        # import json
        # resources = json.loads(f)
        #------------------------------------------------------

        filtered_ressource = []
        for network in resources:
            # import ipdb; ipdb.set_trace()
            match = False
            for item in filter_list:
                # import ipdb; ipdb.set_trace()
                try:
                    test = self.test_value( data_filter[item], network )
                except (KeyError) as e:
                    print (e)
                    return flask.jsonify('Error param name, for ' + item + ' (' + str(e) + ')'), OK

                if test == '-1':
                    return flask.jsonify('Error wrong operator, for ' + item ), OK
                elif test:
                    match = True
                else:
                    match = False
                    break
            if match :
                filtered_ressource.append(network)

        # import ipdb; ipdb.set_trace()
        return flask.jsonify(filtered_ressource), OK


blueprint.add_url_rule(
    '/v1/network_resources/query',
    strict_slashes=False,
    view_func=VirtualisedNetworkQueryAPI.as_view('queryNetwork'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/network_resources',
    strict_slashes=False,
    view_func=VirtualisedNetworkAllocateAPI.as_view('createNetwork'),
    methods=['POST'])

blueprint.add_url_rule(
    '/v1/network_resources',
    strict_slashes=False,
    view_func=VirtualisedNetworkTerminateAPI.as_view('deleteNetwork'),
    methods=['DELETE'])
