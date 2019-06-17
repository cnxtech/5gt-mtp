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

import copy
from pprint import pprint

from nbi.nbi_server import db_session
from db.db_models import Dbdomainlist
import requests
import json
import logging
from future.moves.urllib.parse import urlparse  # python3 compatible
from requests.exceptions import ConnectionError

logger = logging.getLogger('cttc_mtp.sbi.cop_connector')


def get_access(wim):
    """
    Get the ip address to connect with WIM
    :param wim: wim id from database
    :return: wim_url: address of WIM
    """
    wim_values = db_session.query(Dbdomainlist).filter_by(id=wim).first()
    if wim_values is not None:
        wim_url = wim_values.ip + ':' + wim_values.port \
            if wim_values.url is None or not wim_values.url else wim_values.url
        wim_url = urlparse(wim_url).netloc
        return wim_url
    else:
        logger.error("Something wrong with WIMs ID")
        raise KeyError("Something wrong with WIMs ID")


def get_topology(wim):
    """
    Get resource topology of a WIM
    :param wim: wim id in the Dbdomainlist db
    :type wim: str
    :return: topology: dict
    """
    # headers = {'Content-type': 'application/json'}
    url = get_access(wim)
    try:
        response = requests.get('http://{}/restconf/config/context/topology/0'.format(url))
        topology = json.loads(response.content) if response.status_code == 200 else {"Error": "WIM not working!"}
        # print(topology)
        logger.info("Topology of WIM '{}' retrieved.".format(wim))
        return topology
    except ConnectionError as e:
        logger.error(e)
        return


def get_context(wim):
    """
    Query Context to a WIM
    :param wim: wim id in the Dbdomainlist db
    :type wim: str
    :return: topology: dict
    """
    # headers = {'Content-type': 'application/json'}
    url = get_access(wim)
    response = requests.get('http://{}/restconf/config/context'.format(url))
    context = json.loads(response.content) if response.status_code == 200 else {"Error": "WIM not working!"}
    logger.info("Context of WIM '{}' retrieved.".format(wim))
    return context


def create_call(wim, callId, internal_path, inter_wan_path, edge_paths, src_ip, dst_ip, metadata_call, vlan, bw):
    """
    Create a call in COP server
    :param wim: wim id of database
    :param callId: callId COP
    :param internal_path: internal path
    :param inter_wan_path: list of inter-wan links reduced by the PA algorithm
    :param edge_paths: list of edge path between gws and wans
    :param src_ip: IP address of source
    :param dst_ip: IP address of destination
    :param metadata_call: metadata for cop Call
    :param vlan: vlan id
    :param bw: required BW
    :return: response content, status code
    """
    # common part for ARP and IP calls
    # calculate the aEnd of this WIM
    a_end = {}
    if inter_wan_path is not None:
        for link in inter_wan_path:
            # TODO check it when multiple wims
            if link.z_wim_id == wim:
                a_end['nodeId'] = link.z_pe_id
                a_end['edgeEndId'] = str(link.z_link_id)
                a_end['endpointId'] = link.z_pe_id + "_" + str(link.z_link_id)
                # print("an interWan Link in aEnd")
    if not a_end:
        for link in edge_paths:
            if link['zWimId'] == wim and internal_path[0].a_node_id == link['zPEId']:
                a_end['nodeId'] = link['zPEId']
                a_end['edgeEndId'] = link['zLinkId']
                a_end['endpointId'] = link['zPEId'] + "_" + link['zLinkId']
                # print("GW2PE link in aEnd")
    if not a_end:
        logger.error("Something wrong in creating COP call request (aEnd)")
        raise KeyError("Something wrong in creating COP call request (aEnd)")
    else:
        logger.debug("aEnd: {}".format(a_end))
    # calculate the zEnd of this WIM
    z_end = {}
    if inter_wan_path is not None:
        for link in inter_wan_path:
            # TODO check it when multiple wims
            if link.a_wim_id == wim:
                z_end['nodeId'] = link.a_pe_id
                z_end['edgeEndId'] = str(link.a_link_id)
                z_end['endpointId'] = link.a_pe_id + "_" + str(link.a_link_id)
                # print("an interWan Link in zEnd")
    if not z_end:
        for link in edge_paths:
            if link['aWimId'] == wim and internal_path[-1].z_node_id == link['aPEId']:
                z_end['nodeId'] = link['aPEId']
                z_end['edgeEndId'] = link['aLinkId']
                z_end['endpointId'] = link['aPEId'] + "_" + link['aLinkId']
                # print("GW2PE link in zEnd")
    if not z_end:
        logger.error("Something wrong in creating COP call request (zEnd)")
        raise KeyError("Something wrong in creating COP call request (zEnd)")
    else:
        logger.debug("zEnd: {}".format(z_end))
    # build the topoComponent field
    topo_components = [{"nodeId": a_end['nodeId'],
                        "edgeEndId": a_end['edgeEndId'],
                        "endpointId": "0"}]
    for path in internal_path:
        topo_components.append({"nodeId": path.a_node_id,
                                "edgeEndId": str(path.a_link_id),
                                "endpointId": str(len(topo_components))})
        topo_components.append({"nodeId": path.z_node_id,
                                "edgeEndId": str(path.z_link_id),
                                "endpointId": str(len(topo_components))})
    topo_components.append({"nodeId": z_end['nodeId'],
                            "edgeEndId": z_end['edgeEndId'],
                            "endpointId": str(len(topo_components))})
    # print("Forward: {}, {}, {}, {}".format(a_end, z_end, topo_components, metadata_call))
    # defining element for backward calls
    a_end_back = z_end
    z_end_back = a_end
    topo_components_back = copy.deepcopy(topo_components)
    topo_components_back.reverse()
    for i, component in enumerate(topo_components_back):
        component['endpointId'] = str(i)
    metadata_call_back = {'srcMacAddr': metadata_call['dstMacAddr'], 'dstMacAddr': metadata_call['srcMacAddr']}
    # in case of Federation also the vlanId parameter is presetn
    if "vlanId" in metadata_call:
        metadata_call_back['vlanId'] = metadata_call['vlanId']
    # print("Backward: {}, {}, {}, {}".format(a_end_back, z_end_back, topo_components_back, metadata_call_back))
    headers = {'Content-type': 'application/json'}

    # ARP Forward call
    arp_forward_call_body = {
        "callId": callId[0],
        "contextId": "admin",  # TODO check the correct value to be insert
        "transportLayer": {
            "direction": "unidir",
            "layer": "ethernet"
        },
        "trafficParams": {
            "reservedBandwidth": str(bw) + "000000"
        },
        "aEnd": a_end,
        "match": {
            "includePath": {
              "topoComponents": topo_components
            },
            # "ethSrc": "",
            # "ethDst": "",
            "ethType": 2054,
            "arpSpa": src_ip + "/32",
            "arpTpa": dst_ip + "/32",
            "metadata": json.dumps(metadata_call),
            "vlanVid": vlan
        },
        "zEnd": z_end
    }
    logger.debug("ARP Forward Call body: {}".format(arp_forward_call_body))
    # print("ARP Forward Call body: {}".format(arp_forward_call_body))
    url_arp_forward = "http://{}/restconf/config/calls/call/{}".format(get_access(wim), callId[0])

    # ARP Backward call
    arp_backward_call_body = {
        "callId": callId[1],
        "contextId": "admin",  # TODO check the correct value to be insert
        "transportLayer": {
            "direction": "unidir",
            "layer": "ethernet"
        },
        "trafficParams": {
            "reservedBandwidth": str(bw) + "000000"
        },
        "aEnd": a_end_back,
        "match": {
            "includePath": {
              "topoComponents": topo_components_back
            },
            # "ethSrc": "",
            # "ethDst": "",
            "ethType": 2054,
            "arpSpa": dst_ip + "/32",
            "arpTpa": src_ip + "/32",
            "metadata": json.dumps(metadata_call_back),
            "vlanVid": vlan
        },
        "zEnd": z_end_back
    }
    logger.debug("ARP Backward Call body: {}".format(arp_backward_call_body))
    # print("ARP Backward Call body: {}".format(arp_backward_call_body))
    url_arp_backward = "http://{}/restconf/config/calls/call/{}".format(get_access(wim), callId[1])
    try:
        # POST requests to COP server in order to create a ARP calls for forward and backward directions.
        # ARP call forward
        response = requests.post(url_arp_forward, data=json.dumps(arp_forward_call_body), headers=headers)
        # print(response.status_code, response.content)
        logger.info("Call {} to WIM '{}' done".format(arp_forward_call_body['callId'], wim))
        if response.status_code == 201 or response.status_code == 200:
            logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim))
        else:
            logger.error("Error in creating call '{}' in WIM '{}'".format(arp_forward_call_body['callId'], wim))
            return -1
        # ARP call backward
        response = requests.post(url_arp_backward, data=json.dumps(arp_backward_call_body), headers=headers)
        # print(response.status_code, response.content)
        logger.info("Call {} to WIM '{}' done".format(arp_backward_call_body['callId'], wim))
        if response.status_code == 201 or response.status_code == 200:
            logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim))
        else:
            logger.error("Error in creating call '{}' in WIM '{}'".format(arp_backward_call_body['callId'], wim))
            return -1
    except IOError as e:
        logger.error(e)
        raise KeyError("Error in creating call. Check the log")

    # IP Forward call
    ip_forward_call_body = {
        "callId": callId[2],
        "contextId": "admin",  # TODO check the correct value to be insert
        "transportLayer": {
            "direction": "unidir",
            "layer": "ethernet"
        },
        "trafficParams": {
            "reservedBandwidth": str(bw) + "000000"
        },
        "aEnd": a_end,
        "match": {
            "includePath": {
              "topoComponents": topo_components
            },
            # "ethSrc": "",
            # "ethDst": "",
            "ethType": 2048,
            "ipv4Src": src_ip + "/32",
            "ipv4Dst": dst_ip + "/32",
            "metadata": json.dumps(metadata_call),
            "vlanVid": vlan
        },
        "zEnd": z_end
    }
    logger.debug("IP Forward Call body: {}".format(ip_forward_call_body))
    # print("IP Forward Call body: {}".format(ip_forward_call_body))
    url_ip_forward = "http://{}/restconf/config/calls/call/{}".format(get_access(wim), callId[2])

    # IP Backward call
    ip_backward_call_body = {
        "callId": callId[3],
        "contextId": "admin",  # TODO check the correct value to be insert
        "transportLayer": {
            "direction": "unidir",
            "layer": "ethernet"
        },
        "trafficParams": {
            "reservedBandwidth": str(bw) + "000000"
        },
        "aEnd": a_end_back,
        "match": {
            "includePath": {
              "topoComponents": topo_components_back
            },
            # "ethSrc": "",
            # "ethDst": "",
            "ethType": 2048,
            "ipv4Src": dst_ip + "/32",
            "ipv4Dst": src_ip + "/32",
            "metadata": json.dumps(metadata_call_back),
            "vlanVid": vlan
        },
        "zEnd": z_end_back
    }
    logger.debug("IP Backward Call body: {}".format(ip_backward_call_body))
    # print("IP Backward Call body: {}".format(ip_backward_call_body))
    url_ip_backward = "http://{}/restconf/config/calls/call/{}".format(get_access(wim), callId[3])
    try:
        # POST requests to COP server in order to create a IP calls for forward and backward directions.
        # IP call forward
        response = requests.post(url_ip_forward, data=json.dumps(ip_forward_call_body), headers=headers)
        # print(response.status_code, response.content)
        logger.info("Call {} to WIM '{}' done".format(ip_forward_call_body['callId'], wim))
        if response.status_code == 201 or response.status_code == 200:
            logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim))
        else:
            logger.error("Error in creating call '{}' in WIM '{}'".format(ip_forward_call_body['callId'], wim))
            return -1
        # IP call backward
        response = requests.post(url_ip_backward, data=json.dumps(ip_backward_call_body), headers=headers)
        # print(response.status_code, response.content)
        logger.info("Call {} to WIM '{}' done".format(ip_backward_call_body['callId'], wim))
        if response.status_code == 201 or response.status_code == 200:
            logger.info("{} in WIM: '{}'".format(json.loads(response.content), wim))
        else:
            logger.error("Error in creating call '{}' in WIM '{}'".format(ip_backward_call_body['callId'], wim))
            return -1
    except IOError as e:
        logger.error(e)
        raise KeyError("Error in creating call. Check the log")
    # both cop response are ok
    return 0


def delete_call(wim, callIds):
    """
    Create a call in COP server
    :param wim: wim id of database
    :param callIds: callId COP
    :return: 0 if Ok, otherwise raise a Exception
    """
    # ARP call to be deleted (first entry of the list should be ARP)
    for callId in callIds:
        url = "http://{}/restconf/config/calls/call/{}".format(get_access(wim), callId)
        headers = {'Content-type': 'application/json'}
        try:
            # DELETE request to COP server in order to delete the call.
            response = requests.delete(url, headers=headers)
            # print(response.status_code, response.content)
            if response.status_code == 200:
                logger.info("Call '{}' deleted".format(callId))
            else:
                raise KeyError("Response from WIM '{}' is {}!".format(wim, response.status_code))
        except IOError as e:
            logger.error(e)
            raise KeyError("Error in deleting call. Check the log")
    return 0
