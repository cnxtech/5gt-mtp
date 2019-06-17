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

from nbi.nbi_server import db_session
from db.db_models import *
import logging

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
# logicalLinkinterNfviPops part
# from nbi.swagger_server.models.logical_link_inter_nfvi_pops import LogicalLinkInterNfviPops
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner import LogicalLinkInterNfviPopsInner
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links import \
    LogicalLinkInterNfviPopsInnerLogicalLinks
from nbi.swagger_server.models.logical_link_inter_nfvi_pops_inner_logical_links_network_qo_s import \
    LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS


def get_abstraction():
    """
    Get the abstracted resources for the MTP
    :return: nfvi_pops: list of NfviPoPs
            lls_list: list of Logical Links
    """
    logger = logging.getLogger('cttc_mtp.absLogic.get_abstraction')
    r_zone_attr_list = db_session.query(Dbresourceattributes).all()
    nfvi_pops = calc_abs_compute(r_zone_attr_list)
    # define a list of GW that belongs to "local" domain of MTP
    list_gw_not_federated = [net_gw.net_gw_ip_address for pop in nfvi_pops for net_gw in pop.nfvi_pop_attributes.
        network_connectivity_endpoint]
    # logicalLinkInterNfviPops part
    lls_list = []
    lls = db_session.query(Dbllinternfvipops).all()
    for ll in lls:
        if ll.srcGwIpAddress in list_gw_not_federated and ll.dstGwIpAddress in list_gw_not_federated:
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
        else:
            # represents a LL to/from a Federated NFVI-PoP
            logger.info("{} is for federated domain".format(ll))
    return nfvi_pops, lls_list


def get_fed_abstraction():
    """
    Get the abstracted resources for a federated domain
    :return: fed_nfvi_pops: list of Federated NfviPoPs
            fed_lls_list: list of Federated Logical Links
    """
    logger = logging.getLogger('cttc_mtp.absLogic.get_fed_abstraction')
    fed_pops = []
    for fed in db_session.query(Dbnfvipops).filter_by(federated=True).all():
        # building Nfvipop Attributes part
        nfvi_pop_attributes = NfviPopsInnerNfviPopAttributes(
            geographical_location_info=fed.geographicalLocationInfo,
            # vim_id=pop.vimId,  # removed because Federated NFVI-PoP has no VIM associated
            network_connectivity_endpoint=[
                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint(net_gw_ip_address=gtw_ip)
                for
                gtw_ip in fed.networkCE],
            nfvi_pop_id=fed.nfviPopId,
            federated_vim_id=fed.federatedVimId,  # added to extent the call to federation
            resource_zone_attributes=[]  # empty for default in our case
        )
        fed_pops.append(NfviPopsInner(nfvi_pop_attributes=nfvi_pop_attributes))

    # define a list of federated GW
    list_gw_federated = [net_ce.net_gw_ip_address for pop in fed_pops for net_ce in pop.nfvi_pop_attributes.
        network_connectivity_endpoint]
    # print(list_gw_federated)
    fed_lls_list = []
    fed_lls = db_session.query(Dbllinternfvipops).all()
    for ll in fed_lls:
        if ll.srcGwIpAddress in list_gw_federated or ll.dstGwIpAddress in list_gw_federated:
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
            fed_lls_list.append(LogicalLinkInterNfviPopsInner(logical_links=ll_inter_pops_ll))
            logger.info("Collected info for {} federated".format(ll))
        else:
            # represents a LL to/from a Federated NFVI-PoP
            logger.debug("{} is not for federated domain".format(ll))
    return fed_pops, fed_lls_list


def calc_abs_compute(r_zone_attr_list):
    """
    Calculating the abstraction for a list of resource_attributes (VIM/NfviPoP/zoneId)
    :param: r_zone_attr_list: list of resource_zone_attributes
    :return: nfvi_pops: list of NfviPoPs
    """
    logger = logging.getLogger('cttc_mtp.absLogic.calc_abs_compute')
    nfvi_pops = []
    if r_zone_attr_list is not None:
        for r_zone_attr in r_zone_attr_list:
            pop = db_session.query(Dbnfvipops).filter_by(nfviPopId=r_zone_attr.nfviPopId).first()
            if pop is not None:
                vim = db_session.query(Dbdomainlist).filter_by(id=pop.vimId).first()
                if vim is not None:
                    res_id = NfviPopsInnerNfviPopAttributesResourceZoneAttributes(
                        zone_id=r_zone_attr.zoneId,
                        zone_name=r_zone_attr.zoneName,
                        zone_state=r_zone_attr.zoneState,
                        zone_property=r_zone_attr.zoneProperty,
                        metadata=r_zone_attr.metadata_resourceattributes,
                        memory_resource_attributes=NfviPopsInnerNfviPopAttributesMemoryResourceAttributes(
                            available_capacity=r_zone_attr.availableMemory,
                            reserved_capacity=r_zone_attr.reservedMemory,
                            allocated_capacity=r_zone_attr.allocatedMemory,
                            total_capacity=r_zone_attr.totalMemory
                        ),
                        cpu_resource_attributes=NfviPopsInnerNfviPopAttributesCpuResourceAttributes(
                            available_capacity=r_zone_attr.availableCpu,
                            reserved_capacity=r_zone_attr.reservedCpu,
                            allocated_capacity=r_zone_attr.allocatedCpu,
                            total_capacity=r_zone_attr.totalCpu
                        ),
                        storage_resource_attributes=NfviPopsInnerNfviPopAttributesStorageResourceAttributes(
                            available_capacity=r_zone_attr.availableStorage,
                            reserved_capacity=r_zone_attr.reservedStorage,
                            allocated_capacity=r_zone_attr.allocatedStorage,
                            total_capacity=r_zone_attr.totalStorage

                        )
                    )
                    nfvi_pop_already_existing = False
                    for nfvi_pop in nfvi_pops:
                        if nfvi_pop.nfvi_pop_attributes.vim_id == str(vim.id) and \
                                nfvi_pop.nfvi_pop_attributes.nfvi_pop_id == pop.nfviPopId:
                            nfvi_pop_already_existing = True
                            nfvi_pop.nfvi_pop_attributes.resource_zone_attributes.append(res_id)
                            break

                    if not nfvi_pop_already_existing:
                        # building the response for Nfvipop part
                        nfvi_pop_attributes = NfviPopsInnerNfviPopAttributes(
                            geographical_location_info=pop.geographicalLocationInfo,
                            vim_id=pop.vimId,
                            network_connectivity_endpoint=[
                                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint(net_gw_ip_address=gtw_ip)
                                for
                                gtw_ip in pop.networkCE],
                            nfvi_pop_id=pop.nfviPopId,
                            resource_zone_attributes=[res_id]
                        )
                        nfvi_pops.append(NfviPopsInner(nfvi_pop_attributes=nfvi_pop_attributes))
                else:
                    logger.debug('Not a Vim')
                logger.info("Collected info for NFVI-POP '{}' ({})".format(pop.nfviPopId, pop.geographicalLocationInfo))
            else:
                logger.debug('No NfviPop registered for ZoneId: {}'.format(r_zone_attr.zoneId))
    else:
        logger.debug('No ZoneId registered')
    return nfvi_pops


def decompose_ll(logical_link):
    """
    Decompose the LL (to be instantiated)
    :param logical_link: LL to be instantiated
    :return
    """
    # verify the LL to be instantiated really exists in the DB of all possible LL
    ll = db_session.query(Dbllinternfvipops).filter_by(
        logicalLinkId=logical_link['logicalLinkAttributes']['logicalLinkId'],
        srcGwIpAddress=logical_link['logicalLinkAttributes']['srcGwIpAddress'],
        localLinkId=logical_link['logicalLinkAttributes']['localLinkId'],
        dstGwIpAddress=logical_link['logicalLinkAttributes']['dstGwIpAddress'],
        remoteLinkId=logical_link['logicalLinkAttributes']['remoteLinkId']).first()
    if ll is not None:
        # getting the src_gw and dst_gw
        src_gw = logical_link['logicalLinkAttributes']['srcGwIpAddress']
        dst_gw = logical_link['logicalLinkAttributes']['dstGwIpAddress']
        # getting the src and dst NfviPoP associated to upper GW
        # query in the Dbnfvipops for networkCE field (that is a list)
        src_nfvipop = None
        for pop in db_session.query(Dbnfvipops).all():
            if src_gw in pop.networkCE:
                src_nfvipop = pop
                break
        # query in the Dbnfvipops for networkCE field (that is a list)
        dst_nfvipop = None
        for pop in db_session.query(Dbnfvipops).all():
            if dst_gw in pop.networkCE:
                dst_nfvipop = pop
                break
        return [src_nfvipop.nfviPopId, src_gw, dst_nfvipop.nfviPopId, dst_gw]
    else:
        raise KeyError("LL decomposition failed. Check the list of Logical Links in the request.")
