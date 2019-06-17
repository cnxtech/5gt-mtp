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

from random import randint
from ipaddress import ip_address, ip_network

import networkx as nx
from networkx.readwrite import json_graph

from nbi.nbi_server import db_session
from db.db_models import *
from sbi import openstack_connector, cop_connector
import logging
import orchestrator.domResLogic as domResLogic
import orchestrator.absLogic as absLogic
from requests.exceptions import InvalidURL
from sqlalchemy.exc import IntegrityError
from pprint import pprint
from nbi.nbi_server import pa_client
from pa.swagger_client.models import CompRouteInput, CompRouteInputInterWanLinks, CompRouteInputNetwLinkQoS, \
    CompRouteInputAbsWanTopo, CompRouteInputNodes, CompRouteInputEdges, CompRouteInputQosCons


def instantiate_connectivity(connectivity_id, inner_graph, src_pe, dst_pe, src_vnf_ip, dst_vnf_ip, metadata_call, req_bw, req_delay):
    """
    Instantiation of the inter-NFVI-PoP connectivity

    :param connectivity_id
    :type connectivity_id: str
    :param inner_graph
    :type inner_graph: networkx graph
    :param src_pe
    :type src_pe: str
    :param dst_pe:
    :type dst_pe: str
    :param src_vnf_ip:
    :type src_vnf_ip: string
    :param dst_vnf_ip:
    :type dst_vnf_ip: string
    :param metadata_call
    :type metadata_call: dict
    :param req_bw:
    :type req_bw: int
    :param req_delay:
    :type req_delay: int
    :rtype: List[object]
    """
    logger = logging.getLogger('cttc_mtp.ro.inst_connectivity')

    # list_of_wim is a parameter to be sent with connectivity_id to the ServiceDB
    list_of_wim_id = []
    for i, node in inner_graph.nodes(data=True):
        if node['type'] == "WIM":
            list_of_wim_id.append(node['name'])
    # print('List_of_wim: {}'.format(list_of_wim_id))

    # find the list of interWan and Gw2Pe links
    list_of_inter_wan_link = []
    list_of_gw_to_pe_link = []
    for i, j, edge in inner_graph.edges(data=True):
        edge['aPEId'] = inner_graph.node[i]['name']
        edge['zPEId'] = inner_graph.node[j]['name']
        if edge['type'] == "interWanLink":
            list_of_inter_wan_link.append(edge)
        elif edge['type'] == "Gw2PeLink":
            list_of_gw_to_pe_link.append(edge)
    logger.debug("List of interWanLinks: {}".format(list_of_inter_wan_link))
    logger.info("Retrieved list of interWanLinks")

    # retrieve the list of WIM (id of MTP) involved in the LL
    topologies = []
    for wim in list_of_wim_id:
        topologies.append({"wim_id": wim, "topology": cop_connector.get_topology(wim)})
    # pprint(topologies)
    logger.info('Retrieved list of WIM involved in the LL.')
    # CALCULATE THE PATH THROUGH THE PA CLIENT
    pa_response = pa_client.compute_path(connectivity_id,
                                         list_of_inter_wan_link,
                                         topologies,
                                         src_pe,
                                         dst_pe,
                                         req_bw,
                                         req_delay)
    logger.info("PA Response: {}".format(str(pa_response)))
    # TODO check the number of kpaths output of PA, in the following we consider the first path of list
    # create the list of links for the call in order to be depicted in the Resource Viewer
    list_links_call = []
    # inter_wan_links used in the call
    if pa_response.comp_route_output.comp_routes[0].inter_wan_links is not None:
        for interwanlink in pa_response.comp_route_output.comp_routes[0].inter_wan_links:
            list_links_call.append({"source": interwanlink.a_pe_id,
                                    "destination": interwanlink.z_pe_id,
                                    "inter_link_type": "interWanLink"})
    # intrawan_links used in the call
    for intrawanlink in pa_response.comp_route_output.comp_routes[0].wan_paths:
        for element in intrawanlink.wan_path_elements:
            list_links_call.append({"source": element.a_node_id,
                                    "destination": element.z_node_id,
                                    "inter_link_type": "intraWanLink"})
    # print(list_links_call)
    # Retrieve the VLAN through VNF IP ADDRESS and VL table
    list_cidr_vlan = [('192.168.' + str(vl.cidr) + '.0/24', vl.vlanId) for vl in db_session.query(Dbvirtuallinks).all()]
    # removing all the duplicates in the list
    list_cidr_vlan = list(set(list_cidr_vlan))
    vlan_id = ''
    for cidr_vlan in list_cidr_vlan:
        if ip_address(src_vnf_ip) in ip_network(cidr_vlan[0]) and ip_address(dst_vnf_ip) in ip_network(cidr_vlan[0]):
            vlan_id = cidr_vlan[1]
    if not vlan_id:
        logger.error("Something wrong in retrieving the VLAN.")
        raise KeyError("Something wrong in retrieving the VLAN.")
    else:
        logger.info("VLAN Id for the VNFs used: {}".format(vlan_id))

    # retrieve the list of CallId in VL table
    list_used_call_id = []
    interconnectivity_list = db_session.query(Dbinternfvipopconnectivity).all()
    if interconnectivity_list is not None:
        for interconnectivity in interconnectivity_list:
            list_used_call_id.extend(interconnectivity.callIdInvolved)
    # create a list of new call to be instantiated
    list_new_call_id = create_new_call_ids(list_used_call_id)
    # Creating Overall calls for WIM in the PA Response
    list_of_involved_wim = []
    for wan in pa_response.comp_route_output.comp_routes[0].wan_paths:
        # print(wan.wim_id)
        list_of_involved_wim.append(wan.wim_id)
        cop_response = cop_connector.create_call(wim=wan.wim_id,
                                                 callId=list_new_call_id,
                                                 internal_path=wan.wan_path_elements,
                                                 inter_wan_path=pa_response.comp_route_output.comp_routes[0].inter_wan_links,
                                                 edge_paths=list_of_gw_to_pe_link,
                                                 src_ip=src_vnf_ip,
                                                 dst_ip=dst_vnf_ip,
                                                 metadata_call=metadata_call,
                                                 vlan=vlan_id,
                                                 bw=req_bw)
        if cop_response == 0:
            logger.info("Call '{}' Done".format(connectivity_id))
        else:
            error_message = "Something wrong in call for WIM: '{}'".format(wan.wim_id)
            logger.error(error_message)
            # TODO think about a rollback mechanism for instantiating LL (in case one WIM can't set up the call)
            raise KeyError(error_message)

    return list_of_involved_wim, list_links_call, list_new_call_id


def delete_path(inter_nfvi_pop_connectivity_id, list_of_wim):
    """
    Deleting an inter-NFVI-PoP connectivity

    :param inter_nfvi_pop_connectivity_id
    :type inter_nfvi_pop_connectivity_id: str
    :param list_of_wim
    :type list_of_wim: list
    """
    logger = logging.getLogger('cttc_mtp.ro.delete_path')
    # retrieve the list of COP call id to be deleted for a specific inter-nfvi-pop connectivity id
    list_call_ids = db_session.query(Dbinternfvipopconnectivity)\
        .filter_by(interNfviPopConnectivityId=inter_nfvi_pop_connectivity_id).first().callIdInvolved
    logger.info("List of calls to be deleted: {}".format(list_call_ids))
    for wim in list_of_wim:
        # for each WIM send the delete request to COP connector
        response = cop_connector.delete_call(wim, list_call_ids)
        if response != 0:
            raise KeyError("Some error in deleting call '{}' in WIM '{}'".format(inter_nfvi_pop_connectivity_id, wim))
    logger.info("InterNFVIPoP Connectivity ID: '{}' deleted from all the WIM involved".
                format(inter_nfvi_pop_connectivity_id))
    return None


def create_intrapop_net(body_request, metadata_in_body, network_in_db):
    """
    Creating an intra-NFVI-PoP network

    :param body_request: body request
    :param metadata_in_body: dict
    :param network_in_db: list
    :return body_create_net: dict
    """
    logger = logging.getLogger('cttc_mtp.ro.create_intrapop_net')
    # list of vlan and cidr already used in Db (removed the duplicates)
    list_of_vlan = list(set([n.vlanId for n in network_in_db]))
    list_of_cidr = list(set([n.cidr for n in network_in_db]))
    # filter the all() query to the ones with same elements in the request
    # networks_filtered = [n for n in network_in_db if n.vlName == body_request.network_resource_name and
    #                      n.serviceId == metadata_in_body['serviceId']]
    # update 2019-04-29: networks filtered only for network name (and not also for serviceId) in order to
    # develop service composition
    networks_filtered = [n for n in network_in_db if n.vlName == body_request.network_resource_name]
    body_create_net = {
        "name": body_request.network_resource_name,
        "vimId": metadata_in_body['vimId'],
        "floating_ips": eval(metadata_in_body['floating_required'])  # converting str to bool (True/False)
    }
    pool_index = []
    if networks_filtered:
        # case of network with same name/serviceId is already present in the DB.
        for network in networks_filtered:
            if not network.vimId == metadata_in_body['vimId']:
                pool_index.append(network.pool)
            else:
                raise KeyError("Network already in DB VL!")
        # choose a new pool of IP address for the new vimId
        vlan_id = networks_filtered[0].vlanId
        # Update for Federation: added CIDR in intrapop network creation
        if bool(body_request.type_subnet_data.cidr) and not int(body_request.type_subnet_data.cidr.split('.')[2]) == networks_filtered[0].cidr:
            logger.error("Something wrong with CIDR in request.")
            raise KeyError("Error in 'create_intrapop_net'. CIDR in request does not correspond in the MTP db.")
        cidr_id = networks_filtered[0].cidr
        if bool(body_request.type_subnet_data.address_pool):
            net_pool_to_avoid_by_request = eval(body_request.type_subnet_data.address_pool)
            logger.debug("Pool to avoid {}".format(net_pool_to_avoid_by_request))
            pool_index.extend((eval(body_request.type_subnet_data.address_pool)))
        pool_index = sorted(pool_index)
        # loop to create the Pool index ID value
        while True:
            new_pool_index = randint(1, 12)
            if pool_index is None or new_pool_index not in pool_index:
                break
        # new_pool_index = pool_index[-1] + 1
        logger.info("Network already created in other VIMs. VLAN '{}' and CIDR '192.168.{}.0/24'. New pool: '{}'".
                    format(vlan_id, cidr_id, new_pool_index))
        body_create_net["vlan_id"] = vlan_id
        body_create_net["CIDR"] = cidr_id
        body_create_net['pool'] = new_pool_index
    else:
        vlan = create_unique_net_vlan(list_of_vlan)
        if bool(body_request.type_subnet_data.cidr):
            # Update for Federation: added CIDR in intrapop network creation
            logger.debug("CIDR passed in the request. {}".format(body_request.type_subnet_data.cidr))
            cidr = int(body_request.type_subnet_data.cidr.split('.')[2])
        else:
            cidr = create_unique_net_cidr(list_of_cidr)
        if bool(body_request.type_subnet_data.address_pool):
            # Update for Federation: added CIDR in intrapop network creation
            net_pool_to_avoid_by_request = eval(body_request.type_subnet_data.address_pool)
            logger.debug("Pool to avoid {}".format(net_pool_to_avoid_by_request))
            pool_index.extend((eval(body_request.type_subnet_data.address_pool)))
            # loop to create the Pool index ID value
            while True:
                new_pool_index = randint(1, 12)
                if pool_index is None or new_pool_index not in pool_index:
                    break
        else:
            new_pool_index = 0
        logger.info("Network is going to be created. VLAN '{}', CIDR '192.168.{}.0/24', Pool: '{}'".
                    format(vlan, cidr, new_pool_index))
        body_create_net["vlan_id"] = vlan
        body_create_net["CIDR"] = cidr
        body_create_net['pool'] = new_pool_index
    net_ids = openstack_connector.create_os_networks(body_create_net)
    body_create_net['subnet_id'] = net_ids['subnet_id']
    body_create_net['router_id'] = net_ids['router_id']
    body_create_net['network_id'] = net_ids['network_id']
    return body_create_net


def create_unique_net_vlan(vlans_list):
    """
    Create unique network features: VLAN
    :param vlans_list: list

    """
    # loop to create the VLAN ID value
    while True:
        vlan_id = randint(1, 4094)
        if vlans_list is None or vlan_id not in vlans_list:
            break
    return vlan_id


def create_unique_net_cidr(cidrs_list):
    """
    Create unique network features: CIDR of IP address
    :param cidrs_list: list

    """
    # loop to create the CIDR value
    while True:
        cidr_id = randint(0, 255)
        if cidrs_list is None or cidr_id not in cidrs_list:
            break
    return cidr_id


def create_new_call_ids(list_used_calls):
    """
    Create new call Ids for internfvipopconnectivityId
    :param list_used_calls: list

    """
    # create 1st call id
    while True:
        first_id = str(randint(200, 400))
        if list_used_calls is None or first_id not in list_used_calls:
            list_used_calls.append(first_id)
            break
    # create 2nd call id
    while True:
        second_id = str(randint(200, 400))
        if list_used_calls is None or second_id not in list_used_calls:
            list_used_calls.append(second_id)
            break
    # create 3rd call id
    while True:
        third_id = str(randint(200, 400))
        if list_used_calls is None or third_id not in list_used_calls:
            list_used_calls.append(third_id)
            break
    # create 4th call id
    while True:
        fourth_id = str(randint(200, 400))
        if list_used_calls is None or fourth_id not in list_used_calls:
            break
    return [first_id, second_id, third_id, fourth_id]


def delete_intrapop_net(vl_network):
    """
    Deleting an intra-NFVI-PoP network

    :param vl_network: vl DB row
    """
    logger = logging.getLogger('cttc_mtp.ro.delete_intrapop_net')
    body_delete_net = {
        "name": vl_network.vlName,
        "vimId": vl_network.vimId,  # vim.values
        "floating_ips": vl_network.floatingIp,
        "subnet_id": vl_network.subnetId,
        "router_id": vl_network.routerId
    }
    if openstack_connector.delete_os_networks(body_delete_net):
        logger.info("VL '{}' deleted from VIM '{}'".format(body_delete_net['name'], body_delete_net['vimId']))
        return True
    else:
        raise KeyError("Error in deleting VL '{}'".format(body_delete_net['name']))
