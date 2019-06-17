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

import time
from random import randint
from uuid import uuid4

from nbi.nbi_server import db_session
from db.db_models import *
from nbi.swagger_server.models.inter_nfvi_pop_connnectivity_id_list_inner import InterNfviPopConnnectivityIdListInner
from sbi import openstack_connector, cop_connector
import logging
import orchestrator.domResLogic as domResLogic
import orchestrator.absLogic as absLogic
import orchestrator.ro as ro
from networkx.readwrite import json_graph

# Nfvi_pops part
from nbi.swagger_server.models.nfvi_pops_inner import NfviPopsInner
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes import NfviPopsInnerNfviPopAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_network_connectivity_endpoint import \
    NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_resource_zone_attributes import \
    NfviPopsInnerNfviPopAttributesResourceZoneAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_memory_resource_attributes import \
    NfviPopsInnerNfviPopAttributesMemoryResourceAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_cpu_resource_attributes import \
    NfviPopsInnerNfviPopAttributesCpuResourceAttributes
from nbi.swagger_server.models.nfvi_pops_inner_nfvi_pop_attributes_storage_resource_attributes import \
    NfviPopsInnerNfviPopAttributesStorageResourceAttributes
from nbi.swagger_server.models.inline_response200 import InlineResponse200  # noqa: E501
from nbi.swagger_server.models.inline_response201 import InlineResponse201
# logicalLinkinterNfviPops part
# from nbi.swagger_server.models.logical_link_inter_nfvi_pops import LogicalLinkInterNfviPops
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner import LogicalLinkInterNfviPopsInner
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links import \
    LogicalLinkInterNfviPopsInnerLogicalLinks
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links_network_qo_s import \
    LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS

from sqlalchemy.exc import IntegrityError


def get_nfvipops(vims):
    """
    Get the list of NfviPop/ZoneId elements for all the VIMs.
    :param vims: list of VIMs from a sqlalchemy table
    :return: zone_list: list of nfvipops/zones to be queried
    """
    logger = logging.getLogger('cttc_mtp.orch.get_nfvipops')
    zone_list = []
    if vims is not None:
        for vim in vims:
            # logger.info('Collecting for "{}"'.format(vim.name))
            # Retrieve all the NfviPoP associated to the VIM
            # PRELIMINARY ASSUMPTION: 1 NFVI_POP
            pops = db_session.query(Dbnfvipops).filter_by(vimId=vim.id).all()
            for pop in pops:
                if pop is not None:
                    logger.info('Collecting for PoP: "{}" ("{}")'.format(pop.nfviPopId, pop.geographicalLocationInfo))
                    # Retrieve all the zoneIDs associated to the NFVIPoP
                    # PRELIMINARY ASSUMPTION: 1 ZONE_ID
                    r_zone_attr_list = db_session.query(Dbresourceattributes).filter_by(
                        nfviPopId=pop.nfviPopId).all()
                    for r_zone_attr in r_zone_attr_list:
                        zone_list.append((vim, pop, r_zone_attr))
    return zone_list


def retrieve_mtp_resources():
    """
    Retrieve all the Resources (compute and networking) for the MTP.
    :return: nfvi_pops
    :return: lls_list
    """
    logger = logging.getLogger('cttc_mtp.orch.retrieve_mtp_resources')
    # get the list of VIMs from DB
    vims = db_session.query(Dbdomainlist).filter_by(type="VIM").all()
    zones = get_nfvipops(vims)
    logger.info("Retrieved all the list of VIM/NfviPoP/Zone elements")
    if domResLogic.get_aggregate(zones) != -1:
        return absLogic.get_abstraction()
    else:
        message = "Error in retrieving MTP resources"
        logger.error(message)
        raise TypeError(message)


def retrieve_mtp_federated_resources():
    """
    Retrieve all the Federated Resources (compute and networking) for the MTP.
    :return: nfvi_pops
    :return: lls_list
    """
    logger = logging.getLogger('cttc_mtp.orch.retrieve_mtp_federated_resources')
    return absLogic.get_fed_abstraction()


def create_connectivity(body):
    """Create inter-NfviPop Connectivity

    :param body: Create inter-NfviPop Connectivity
    :type body: InterNfviPopConnectivityRequest

    """
    logger = logging.getLogger('cttc_mtp.orch.create_connectivity')
    conn_list = []
    # print(body)
    ll_attributes = body.logical_link_path_list
    net_layer = body.network_layer
    net_type = body.inter_nfvi_pop_network_type
    for ll_attr in ll_attributes:
        # decompose the LL to retrieve NfviPoP and GW involved
        ll_endpoint = absLogic.decompose_ll(ll_attr)
        # decompose the stitching table in order to retrieve the internal PEs and WIMs
        # creating a nx graph from stitching table
        total_graph = domResLogic.decompose_stitching()
        # Reducing the graph between Src Gw and Dst Gw
        src_gw = ll_endpoint[1]
        dst_gw = ll_endpoint[3]
        # Reducing the graph between the two GWs (in order to remove the NFVIPOP connected to the GW
        # In this way it remains only one adjacent node connected to the GW (and for assumption is a PE)
        inner_graph = domResLogic.reducing_graph(total_graph, src_gw, dst_gw)
        # retrieve the index of source and destination GWs in the graph
        node_src_gw = [x for x, y in inner_graph.nodes(data=True) if y['name'] == src_gw]
        node_dst_gw = [x for x, y in inner_graph.nodes(data=True) if y['name'] == dst_gw]
        # find adjacents node (PEs) of SRC and DST GW -> having reduced the graph is only one node i.e. a PE
        adjacents_to_src_gw = [n for n in inner_graph.neighbors(node_src_gw[0])]
        adjacents_to_dst_gw = [n for n in inner_graph.neighbors(node_dst_gw[0])]
        # ASSUMPTION: ONE PE CONNECTED TO ONE GW
        src_pe_id = inner_graph.node[adjacents_to_src_gw[0]]['name']
        dst_pe_id = inner_graph.node[adjacents_to_dst_gw[0]]['name']
        # print(src_pe_id, dst_pe_id)
        # retrieve the ServiceID related to the LL
        meta_data = body.meta_data
        service_id_in_metadata = (False, '')
        for meta_data_element in meta_data:
            if meta_data_element['key'] == "ServiceId":
                service_id_in_metadata = (True, meta_data_element['value'])
                logger.info("ServiceId in the request: '{}'".format(service_id_in_metadata[1]))

        if not service_id_in_metadata[0]:
            # case of ServiceId not defined in the request
            logger.error("ServiceId wrong in the request")
            raise KeyError("Check the ServiceId in the request")
        # retrieve the IP address
        src_vnf_ip = ''
        dst_vnf_ip = ''
        src_mac_add = ''
        dst_mac_add = ''
        vlan_federation = None
        for metadata_vnf in ll_attr['metaData']:
            if metadata_vnf['key'] == "srcVnfIpAddress":
                src_vnf_ip = metadata_vnf['value']
            if metadata_vnf['key'] == "dstVnfIpAddress":
                dst_vnf_ip = metadata_vnf['value']
            if metadata_vnf['key'] == "srcVnfMacAddress":
                src_mac_add = metadata_vnf['value']
            if metadata_vnf['key'] == "dstVnfMacAddress":
                dst_mac_add = metadata_vnf['value']
            # vlanId paramter in metadata sent in case of Federation
            if metadata_vnf['key'] == "vlanId":
                vlan_federation = int(metadata_vnf['value'])
        # TODO check if the previous values are ip/mac complaint
        if not src_vnf_ip or not dst_vnf_ip or not src_mac_add or not dst_mac_add:
            err = "'srcVnfIpAddress', 'dstVnfIpAddress', " \
                  "'srcVnfMacAddress' and 'dstVnfMacAddress' are mandatory parameters"
            logger.error(err)
            raise KeyError(err)
        logger.info('VNF IP ADDRESS (SRC) (DST): {} {}'.format(src_vnf_ip, dst_vnf_ip))
        logger.info('VNF MAC ADDRESS (SRC) (DST): {} {}'.format(src_mac_add, dst_mac_add))
        # creating metadata for call request on mac addresses
        metadata_call = {"srcMacAddr": src_mac_add, "dstMacAddr": dst_mac_add}
        # add the vlanId parameter to metadata of COP call in case of Federation
        if vlan_federation is not None:
            logger.info("VLAN Id to be used for Federation Issue: {}".format(vlan_federation))
            metadata_call['vlanId'] = vlan_federation
        # Creating id for inter NfviPop connectivity
        connectivity_id = create_connectivity_id()
        # instantiation of the new Inter-NFVIPoP Connectivity in the RO package
        list_of_wim, list_links_call, list_new_call_ids = ro.instantiate_connectivity(connectivity_id=connectivity_id,
                                                                                      inner_graph=total_graph,
                                                                                      src_pe=src_pe_id,
                                                                                      dst_pe=dst_pe_id,
                                                                                      src_vnf_ip=src_vnf_ip,
                                                                                      dst_vnf_ip=dst_vnf_ip,
                                                                                      metadata_call=metadata_call,
                                                                                      req_bw=ll_attr['reqBandwidth'],
                                                                                      req_delay=ll_attr['reqLatency'])

        # append the links between GW-PE of source and destination of the LL
        # The following lines represent the call from GWs to PEs elements,
        # it works but i don't know if it worth draw it
        # list_links_call.extend([{"source": src_gw, "destination": src_pe_id, "inter_link_type": "interWanLink"},
        #                         {"source": dst_pe_id, "destination": dst_gw, "inter_link_type": "interWanLink"}])
        db_session.add(Dbinternfvipopconnectivity(
            interNfviPopConnectivityId=connectivity_id,
            callIdInvolved=list_new_call_ids,
            logicalLinkId=ll_attr['logicalLinkAttributes']['logicalLinkId'],
            srcGwIpAddress=ll_attr['logicalLinkAttributes']['srcGwIpAddress'],
            localLinkId=ll_attr['logicalLinkAttributes']['localLinkId'],
            dstGwIpAddress=ll_attr['logicalLinkAttributes']['dstGwIpAddress'],
            remoteLinkId=ll_attr['logicalLinkAttributes']['remoteLinkId'],
            reqBandwidth=ll_attr['reqBandwidth'],
            reqLatency=ll_attr['reqLatency'],
            networkLayer=net_layer,
            interNfviPopNetworkType=net_type,
            metadata_interconnectivity='',
            interNfviPopNetworkSegmentType='',
            serviceId=service_id_in_metadata[1],
            wimInvolved=list_of_wim,
            pathCall=str(list_links_call)
        ))
        logger.info("Created interNfviPop Connectivity with id: {}".format(connectivity_id))
        conn_list.append(InlineResponse201(inter_nfvi_pop_connnectivity_id=connectivity_id,
                                           inter_nfvi_pop_network_segment_type='p2p'))
        # time.sleep(5)
        db_session.commit()

    return conn_list


def create_connectivity_id():
    """
    Create a new unique Inter-NFVIPoP Connectivity Id
    :return connectivity_id : unique Inter-NFVIPoP Connectivity Id
    """
    logger = logging.getLogger('cttc_mtp.orch.retrieve_mtp_resources')
    connectivity_id = None
    new_connectivity_id = False
    while not new_connectivity_id:
        connectivity_id = str(uuid4())[:8]
        # check in the DB
        new_connectivity_id = True if db_session.query(Dbinternfvipopconnectivity).filter_by(
            interNfviPopConnectivityId=connectivity_id).first() is None else False
    logger.info("Created the inter-NFVIPoP connectivity id: '{}'".format(connectivity_id))
    return connectivity_id


def retrieve_mtp_resources_old():
    """
    Retrieve all the Resouces (compute and networking) for the MTP.
    :return: nfvi_pops
    :return: lls_list
    """
    logger = logging.getLogger('cttc_mtp.orch.retrieve_mtp_resources')
    # get the list of VIMs from DB
    vims = db_session.query(Dbdomainlist).filter_by(type="VIM").all()

    nfvi_pops = []
    if vims is not None:
        for vim in vims:
            logger.info('Collecting for "{}"'.format(vim.name))
            # ASSUMPTION: 1 NFVI_POP
            pop = db_session.query(Dbnfvipops).filter_by(vimId=vim.id).first()
            if pop is not None:
                logger.info('Collecting for "{}"'.format(pop.nfviPopId))
                resource_zone_attributes = []
                # ASSUMPTION: 1 ZONE_ID
                r_zone_attr = db_session.query(Dbresourceattributes).filter_by(
                    zoneId=pop.resourceZoneAttributes).first()
                if r_zone_attr is not None:
                    logger.info('Collecting for "{}/{}/{}"'.format(vim.name, pop.nfviPopId, r_zone_attr.zoneName))
                    os_resources = openstack_connector.retrieve_resource(vim)

                    res_id = NfviPopsInnerNfviPopAttributesResourceZoneAttributes(
                        zone_id=r_zone_attr.zoneId,
                        zone_name=r_zone_attr.zoneName,
                        zone_state=r_zone_attr.zoneState,
                        zone_property=r_zone_attr.zoneProperty,
                        metadata=r_zone_attr.metadata_resourceattributes,
                        memory_resource_attributes=NfviPopsInnerNfviPopAttributesMemoryResourceAttributes(
                            available_capacity=os_resources['mem']['available'],
                            reserved_capacity=os_resources['mem']['reserved'],
                            allocated_capacity=os_resources['mem']['allocated'],
                            total_capacity=os_resources['mem']['total']
                        ),
                        cpu_resource_attributes=NfviPopsInnerNfviPopAttributesCpuResourceAttributes(
                            available_capacity=os_resources['cpu']['available'],
                            reserved_capacity=os_resources['cpu']['reserved'],
                            allocated_capacity=os_resources['cpu']['allocated'],
                            total_capacity=os_resources['cpu']['total']
                        ),
                        storage_resource_attributes=NfviPopsInnerNfviPopAttributesStorageResourceAttributes(
                            available_capacity=os_resources['storage']['available'],
                            reserved_capacity=os_resources['storage']['reserved'],
                            allocated_capacity=os_resources['storage']['allocated'],
                            total_capacity=os_resources['storage']['total']

                        )
                    )
                    resource_zone_attributes.append(res_id)
                    # updating the resourceAttributeDb with the last query to VIM
                    try:
                        r_zone_attr.availableMemory = os_resources['mem']['available']
                        r_zone_attr.reservedMemory = os_resources['mem']['reserved']
                        r_zone_attr.allocatedMemory = os_resources['mem']['allocated']
                        r_zone_attr.totalMemory = os_resources['mem']['total']
                        r_zone_attr.availableCpu = os_resources['cpu']['available']
                        r_zone_attr.reservedCpu = os_resources['cpu']['reserved']
                        r_zone_attr.allocatedCpu = os_resources['cpu']['allocated']
                        r_zone_attr.totalCpu = os_resources['cpu']['total']
                        r_zone_attr.availableStorage = os_resources['storage']['available']
                        r_zone_attr.reservedStorage = os_resources['storage']['reserved']
                        r_zone_attr.allocatedStorage = os_resources['storage']['allocated']
                        r_zone_attr.totalStorage = os_resources['storage']['total']
                        db_session.commit()
                    except IntegrityError:
                        logger.error('Something wrong with the database')
                        db_session.rollback()

                    # building the response for Nfvipop part
                    nfvi_pop_attributes = NfviPopsInnerNfviPopAttributes(
                        geographical_location_info=pop.geographicalLocationInfo,
                        vim_id=pop.vimId,
                        network_connectivity_endpoint=[
                            NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint(net_gw_ip_address=gtw_ip) for
                            gtw_ip in pop.networkCE.split(';')],
                        nfvi_pop_id=pop.nfviPopId,
                        resource_zone_attributes=resource_zone_attributes
                    )
                    nfvi_pops.append(NfviPopsInner(nfvi_pop_attributes=nfvi_pop_attributes))
                    logger.info("Collected all info for {}".format(pop))
                else:
                    logger.debug('No ZoneId registered for Nfvipop: {}'.format(pop.nfviPopId))
            else:
                logger.debug('No NfviPop registered for VIM: {}/{}'.format(vim.id, vim.name))
    else:
        logger.debug('Not VIMs are configured')

    # logicalLinkInterNfviPops part
    lls_list = []
    lls = db_session.query(Dbllinternfvipops).all()
    for ll in lls:
        ll_inter_pops_ll_qos = LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS(
            link_cost_value=ll.linkCostValue,
            link_cost=ll.linkCost,
            link_delay_value=ll.linkDelayValue,
            link_delay=ll.linkDelay
        )
        ll_inter_pops_ll = LogicalLinkInterNfviPopsInnerLogicalLinks(
            logical_link_id=ll.logicalLinkId,
            total_bandwidth=ll.totalBandwidth,
            available_bandwidth=ll.availableBandwidth,
            network_qo_s=ll_inter_pops_ll_qos,
            src_gw_ip_address=ll.srcGwIpAddress,
            local_link_id=ll.localLinkId,
            dst_gw_ip_address=ll.dstGwIpAddress,
            remote_link_id=ll.remoteLinkId,
            network_layer=ll.networkLayer,
            inter_nfvi_pop_network_type=ll.interNfviPopNetworkType,
            inter_nfvi_pop_network_topology=ll.interNfviPopNetworkTopology

        )
        lls_list.append(LogicalLinkInterNfviPopsInner(logical_links=ll_inter_pops_ll))
        logger.info("Collected all info for {}".format(ll))
    return nfvi_pops, lls_list


def delete_connectivity(body):
    """
    Deleted a InterNFVI-POP request
    :param body: request
    """
    logger = logging.getLogger('cttc_mtp.orch.delete_connectivity')
    meta_data = body.meta_data
    # retrieve the ServiceID related to the LL
    service_id_in_metadata = (False, '')
    for meta_data_element in meta_data:
        if meta_data_element['key'] == "ServiceId":
            service_id_in_metadata = (True, meta_data_element['value'])
            logger.info("ServiceId in the request: '{}'".format(service_id_in_metadata[1]))

    if not service_id_in_metadata[0]:
        # case of ServiceId not defined in the request
        logger.error("ServiceId wrong in the request")
        raise KeyError("Check the ServiceId in the request")

    # find all the Inter-NFVIPoP connectivity IDs associated to the ServiceId in the body
    conn_to_be_deleted = db_session.query(Dbinternfvipopconnectivity).filter_by(serviceId=service_id_in_metadata[1])\
        .all()
    if not conn_to_be_deleted:
        raise KeyError("ServiceId in the request not in DB.")
    else:
        # retrieve just the list of IDs
        list_conn_to_be_deleted = [elem.interNfviPopConnectivityId for elem in conn_to_be_deleted]

        # find the list of Inter-NFVIPoP connectivity IDs in the body
        list_of_internfvipopcon = []
        for inter_nfvi_pop_connectivity in body.inter_nfvi_pop_connnectivity_id_list:
            list_of_internfvipopcon.append(InterNfviPopConnnectivityIdListInner
                                           .from_dict(inter_nfvi_pop_connectivity).inter_nfvi_pop_connnectivity_id)
        # Delete all the InterNFVI-PoP Connectivity Id in the list of DELETE request
        for id in list_of_internfvipopcon:
            element = db_session.query(Dbinternfvipopconnectivity).filter_by(serviceId=service_id_in_metadata[1],
                                                                             interNfviPopConnectivityId=id).first()
            if element:
                # delete path in all the WIM involved
                ro.delete_path(element.interNfviPopConnectivityId, element.wimInvolved)
                # once delete the path, remove the entry in the DB
                try:
                    db_session.delete(element)
                    db_session.commit()
                    logger.info("Removed the entry in ServiceId Table")
                except IntegrityError:
                    db_session.rollback()
                    logger.error('Error in database operation')
            else:
                raise ValueError("InterNfviPoPConnectivity Id '{}' is not associated to serviceId '{}'!".
                                 format(id, service_id_in_metadata[1]))

        if sorted(list_conn_to_be_deleted) == sorted(list_of_internfvipopcon):
            logger.info("All the InterNfviPopConnnectivityId associated to ServiceId '{}' are in the DELETE request".
                        format(service_id_in_metadata[1]))
        else:
            logger.info("Some InterNfviPoPConnectivityId associated to Service Id '{}' are not in the DELETE request".
                        format(service_id_in_metadata[1]))
        return None


def create_vl_network(body):
    """
    Create a VL network in MTP
    :param body: request
    """
    logger = logging.getLogger('cttc_mtp.orch.create_vl_network')
    # print(body)
    metadata = {}
    for element in body.metadata:
        metadata[element.key] = element.value
    # check if "ServiceId", "floating_required" and "vimId" are in the metadata
    if "vimId" not in metadata or "serviceId" not in metadata or "floating_required" not in metadata:
        raise KeyError("'serviceId', 'floating_required' and 'vimId' parameters are mandatory in metadata.")
    # check if the selected VIM is inside the Domain DB
    vim_exist = db_session.query(Dbdomainlist).filter_by(id=metadata['vimId']).first()
    if vim_exist is None or vim_exist.type == "WIM":
        raise KeyError("vimId not exist or does not correspond to a VIM")
    logger.info("Request to create vl network '{}' for serviceid '{}' in VIM '{}'".format(body.network_resource_name,
                                                                                          metadata['serviceId'],
                                                                                          metadata['vimId']))
    # network_in_db = db_session.query(Dbvirtuallinks).all()
    net_created = ro.create_intrapop_net(body, metadata, db_session.query(Dbvirtuallinks).all())
    logger.info("VL network '{}' created.".format(body.network_resource_name))
    # updating the DB
    try:
        db_session.add(Dbvirtuallinks(vlName=net_created['name'],
                                      serviceId=metadata['serviceId'],
                                      vimId=net_created['vimId'],
                                      floatingIp=net_created['floating_ips'],
                                      networkId=net_created['network_id'],
                                      subnetId=net_created['subnet_id'],
                                      routerId=net_created['router_id'],
                                      cidr=net_created['CIDR'],
                                      vlanId=net_created['vlan_id'],
                                      pool=net_created['pool']))
        db_session.commit()
        logger.info("DB updated.")
    except IntegrityError:
        db_session.rollback()
        logger.error("Error in DB")
        raise KeyError("Error in loading DB")
    return net_created


def delete_vl_network(network_id):
    """
    Delete a VL network in MTP
    :param network_id: id of network to be deleted
    """
    logger = logging.getLogger('cttc_mtp.orch.delete_vl_network')
    vl_to_be_deleted = db_session.query(Dbvirtuallinks).filter_by(networkId=network_id).first()
    if vl_to_be_deleted is not None:
        ro.delete_intrapop_net(vl_to_be_deleted)
    else:
        raise KeyError("NetworkReourceId not in DB")
    try:
        db_session.delete(vl_to_be_deleted)
        db_session.commit()
        logger.info('Deleted "{}".'.format(network_id))
    except IntegrityError:
        db_session.rollback()
        logger.error('Error in database operation')
    return 0
