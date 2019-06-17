# Copyright 2019 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Author: Luca Vettori


"""
Implements some utils function for Openstack

"""
import time

from future.moves.urllib.parse import urlparse  # python3 compatible
import requests
import json
import logging
from nbi.nbi_server import db_session
from db.db_models import *

logger = logging.getLogger('cttc_mtp.sbi.openstack_connector')


def query_resources(vim_values, start_time, end_time):
    """
    Retrieve resources of an Openstack
    :param start_time: start time analysis
    :param end_time: stop time analysis
    :param vim_values: vim data from database
    :return: tuple with nova, volume3 resources response request.
    """
    if vim_values is None:
        raise AttributeError('Openstack not well configured')
    else:
        project_id, token = token_request(vim_values)
        headers = {'X-Auth-Token': token}
        vim_url = urlparse(vim_values.url)
        # request of info to Nova Module of Openstack
        # nova_resource = get('http://{}/compute/v2.1/os-hypervisors/detail'.format(vim_url.netloc), headers=headers)
        nova_resource = requests.get('http://{}/compute/v2.1/{}/limits'.format(vim_url.netloc, project_id),
                                     headers=headers)
        nova_resource_statistic = requests.get(
            'http://{}/compute/v2.1/os-hypervisors/statistics'.format(vim_url.netloc),
            headers={'X-Auth-Token': token})
        # request of info to Volume Module of Openstack
        volume3_resource = requests.get('http://{}/volume/v3/{}/limits'.format(vim_url.netloc, project_id),
                                        headers=headers)
        if start_time is None and end_time is None:
            simple_tenant_usage_resource_url = ('http://{}/compute/v2.1/{}/os-simple-tenant-usage/{}'
                                                .format(vim_url.netloc, project_id, project_id))
        else:
            simple_tenant_usage_resource_url = ('http://{}/compute/v2.1/{}/os-simple-tenant-usage/{}?start={}&end={}'
                                                .format(vim_url.netloc, project_id, project_id, start_time, end_time))
        simple_tenant_usage_resource = requests.get(simple_tenant_usage_resource_url, headers=headers)
        network = requests.get('http://' + vim_url.netloc + ':9696/v2.0/networks',
                               headers=headers)

        return nova_resource, volume3_resource, simple_tenant_usage_resource, network, nova_resource_statistic


def token_request(vim_values):
    """
    Retrieve token of an Openstack
    :param vim_values: vim data from database
    :return: tuple with project_id, token.
    """
    # Request for a Token to an Openstack server
    # Header of the request
    headers = {'Content-type': 'application/json'}
    # Body of the request
    body_for_token = {
        "auth": {
            "identity": {
                "methods": [
                    "password"
                ],
                "password": {
                    "user": {
                        "name": vim_values.username,
                        "domain": {
                            "name": "Default"
                        },
                        "password": vim_values.userpassword
                    }
                }
            }
            # , "scope": {
            #           "project": {
            #               "domain": {
            #                   "id": "default"
            #               },
            #               "name": "osm-project54"
            #           }
            #       }
        }
    }
    token_response = requests.post(vim_values.url + '/auth/tokens',
                                   data=json.dumps(body_for_token),
                                   headers=headers)
    # Token is in the response headers
    token = token_response.headers['X-Subject-Token']
    logger.info('Token retrieved')
    # internal Openstack id for  the project related to the VIM
    project_id = json.loads(token_response.content.decode('utf-8'))['token']['project']['id']
    return project_id, token


def retrieve_resource(vim):
    """
    Retrieve resource an Openstack, divided for mem/cpu/storage
    :param vim: vim data from database
    :return: dict with mem/cpu/storage.
    """
    mem = {}
    resources = query_resources(vim, None, None)
    nova_res = json.loads(resources[0].content)['limits']['absolute']
    mem['allocated'] = nova_res['totalRAMUsed']
    mem['total'] = nova_res['maxTotalRAMSize']
    mem['available'] = nova_res['maxTotalRAMSize'] - nova_res['totalRAMUsed']
    # TODO check reserved item
    mem['reserved'] = 0
    cpu = {}
    nova_res = json.loads(resources[0].content)['limits']['absolute']
    cpu['allocated'] = nova_res['totalCoresUsed']
    cpu['total'] = nova_res['maxTotalCores']
    cpu['available'] = nova_res['maxTotalCores'] - nova_res['totalCoresUsed']
    # TODO check reserved item
    cpu['reserved'] = 0
    storage = {}
    volume_res = json.loads(resources[1].content)['limits']['absolute']
    storage['allocated'] = volume_res['totalGigabytesUsed']
    storage['total'] = volume_res['maxTotalVolumeGigabytes']
    storage['available'] = volume_res['maxTotalVolumeGigabytes'] - volume_res['totalGigabytesUsed']
    # TODO check reserved item
    storage['reserved'] = 0
    logger.info('Resources retrieved!')
    return {'mem': mem,
            'cpu': cpu,
            'storage': storage}


def get_status_os_network(info):
    """
    Get the status of network in Openstack (created in this framework)
    :param info: dict
    :return: bool, dict
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(id=info['vimId']).first()
    # retrieve the token for OS
    project_id, token = token_request(vim_values)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    vim_url = urlparse(vim_values.url)
    network_response = requests.get('http://{}:9696/v2.0/networks/{}'.format(vim_url.netloc, info['netId']),
                                    headers=headers)
    if network_response.status_code == 200:
        network_response = network_response.json()
        # print(network_response)
        return True, network_response['network']
    else:
        # print(network_response.json()['NeutronError'])
        logger.debug(network_response.json()['NeutronError'])
        logger.error("Error in getting network resource for VIM")
        return False, network_response.json()['NeutronError']


def create_os_networks(info_to_create_networks):
    """
    Create a network on a provider network in Openstack
    :param info_to_create_networks: dict
    :return: dict (of ids)
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(
        id=info_to_create_networks['vimId']).first()  # to be done in MTP more internal
    # retrieve the token for OS
    project_id, token = token_request(vim_values)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    vim_url = urlparse(vim_values.url)

    # find uuid of provider_network
    provider_network = 'public'  # it could be other name
    # provider_network_floating = 'publicFloating'
    provider_network_floating = 'public'
    provider_network_uuid = ''
    network_response = requests.get('http://' + vim_url.netloc + ':9696/v2.0/networks', headers=headers)
    if network_response.status_code == 200:
        network_response = network_response.json()
    else:
        logger.error("Error in getting network resource for VIM")

    # retrieve the provider network uuid
    for net in network_response['networks']:
        if net['name'] == provider_network_floating:
            provider_network_uuid = net['id']

    # create the network in OS
    body_network = {
        "network": {
            "admin_state_up": 'true',
            "name": info_to_create_networks["name"],
            "provider:network_type": "vlan",
            "provider:physical_network": provider_network,
            "provider:segmentation_id": info_to_create_networks["vlan_id"],
            "shared": "true",
            "router:external": "true"
        }
    }

    response_network = requests.post("http://{}:9696/v2.0/networks".format(vim_url.netloc),
                                     data=json.dumps(body_network),
                                     headers=headers)
    if response_network.status_code == 201:
        response_network = response_network.json()
    else:
        logger.error(response_network.content)
        raise KeyError("Error in creating Network")
    network_id = response_network['network']['id']
    network_name = response_network['network']['name']
    logger.info("Network '{}' created with id: {}".format(network_name, network_id))
    # create the subnet
    # be careful with dhcps, they consume ips and they can be in other vim's!!
    if info_to_create_networks["pool"] == 0:
        start_ip = 2  # because by default OS create the internal GW to the "xxx.xxx.xxx.1" ip-address
    else:
        start_ip = 1 + 20 * info_to_create_networks["pool"]
    # last pool (12) end to 254
    if info_to_create_networks["pool"] == 12:
        end_ip = 254
    else:
        end_ip = 20 * info_to_create_networks["pool"] + 20
    logger.debug("Pool {}: IP from {} to {}".format(info_to_create_networks['pool'], start_ip, end_ip))
    body_subnet = {
        "subnet": {
            "network_id": response_network['network']['id'],
            "name": response_network['network']['name'] + "-subnet",
            "ip_version": 4,
            "cidr": "192.168." + str(info_to_create_networks['CIDR']) + ".0/24",
            "allocation_pools": [
                {
                    "start": "192.168." + str(info_to_create_networks['CIDR']) + '.' + str(start_ip),
                    "end": "192.168." + str(info_to_create_networks['CIDR']) + '.' + str(end_ip)}
            ],
            "gateway_ip": "192.168." + str(info_to_create_networks['CIDR']) + ".1",
            "enable_dhcp": 'true'
        }
    }

    response_subnet = requests.post("http://{}:9696/v2.0/subnets".format(vim_url.netloc),
                                    data=json.dumps(body_subnet),
                                    headers=headers)
    if response_subnet.status_code == 201:
        response_subnet = response_subnet.json()
        logger.info("Subnet '{}' created successfully with id: {}".format(body_subnet['subnet']['name'],
                                                                          response_subnet['subnet']['id']))
    else:
        logger.error(response_subnet.json())
        raise KeyError("Error in creating Sub-Network: {}".format(response_subnet.content))

    subnet_id = response_subnet['subnet']['id']
    # here, I create the router if necessary
    router_id = ""
    if info_to_create_networks['floating_ips']:
        body_router = {
                 "router": {
                             "name": "router_net_" + response_network['network']['name'],
                             "description": "router between external network '{}' and vld '{}'".format(provider_network,
                                                                                                       network_name),
                             "external_gateway_info": {
                                 "network_id": provider_network_uuid,  # mandatory an uuid
                                 "enable_snat": "true",
                              },
                             "availability_zone_hints": [],
                             "admin_state_up": "true"
                            }
               }
        response_router = requests.post("http://{}:9696/v2.0/routers".format(vim_url.netloc),
                                        data=json.dumps(body_router),
                                        headers=headers)
        if response_router.status_code == 201:
            response_router = response_router.json()
            logger.info("Router '{}' created successfully with id: '{}'".format(body_router['router']['name'],
                                                                                response_router['router']['id']))
        else:
            logger.error("Error in creating Router")
            raise KeyError("Error in creating Router")
        router_id = response_router['router']['id']
        body_subnet_router = {
            "subnet_id": subnet_id
        }
        response_subnet_router = requests.put(
            "http://{}:9696/v2.0/routers/{}/add_router_interface".format(vim_url.netloc, router_id),
            data=json.dumps(body_subnet_router), headers=headers)
        if response_subnet_router.status_code == 200:
            logger.info("Add subnet '{}' to router '{}'".format(body_subnet['subnet']['name'],
                                                                body_router['router']['name']))
        else:
            raise KeyError("Error in adding subnet to router")
    # finishing creating the router
    return {"subnet_id": subnet_id, "router_id": router_id, "network_id": network_id}


def delete_os_networks(info_to_delete_networks):
    """
    Delete a network in Openstack
    :param info_to_delete_networks: dict
    :return: boolean
    """
    vim_values = db_session.query(Dbdomainlist).filter_by(
        id=info_to_delete_networks['vimId']).first()  # to be done in MTP more internal
    # retrieve the token for OS
    project_id, token = token_request(vim_values)
    headers = {
        "X-Auth-Token": token,
        "Content-Type": "application/json",
        'Accept': 'application/json'
    }
    vim_url = urlparse(vim_values.url)

    # retrieve the network_id of the network created
    network_response = requests.get('http://' + vim_url.netloc + ':9696/v2.0/networks', headers=headers)
    if network_response.status_code == 200:
        network_response = network_response.json()
    else:
        logger.error("Error in getting network resource for VIM")
        return False

    # retrieve the provider network uuid
    network_id = ''
    for net in network_response['networks']:
        if net['name'] == info_to_delete_networks['name']:
            network_id = net['id']

    if info_to_delete_networks['floating_ips']:
        # router_name = 'router_net_' + info_to_delete_networks['name']
        router_id = info_to_delete_networks['router_id']
        body_remove_subnet = {
                "subnet_id": info_to_delete_networks['subnet_id']
        }
        remove_subnet_from_router = requests.put(
            "http://{}:9696/v2.0/routers/{}/remove_router_interface".format(vim_url.netloc, router_id),
            data=json.dumps(body_remove_subnet), headers=headers)
        delete_router = requests.delete("http://{}:9696/v2.0/routers/{}".format(vim_url.netloc, router_id),
                                        headers=headers)
        if delete_router.status_code != 204:
            logger.error("Something went wrong in deleting network: '{}'".format(delete_router.json()['NeutronError']))
            return False
        time.sleep(5)  # it takes time to remove a router

    response_delete_net = requests.delete("http://{}:9696/v2.0/networks/{}".format(vim_url.netloc, network_id),
                                          headers=headers)
    if response_delete_net.status_code != 204:
        logger.error("Something went wrong in deleting network: '{}'".format(
            response_delete_net.json()['NeutronError']))
        return False
    else:
        return True
