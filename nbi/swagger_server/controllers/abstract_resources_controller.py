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
import datetime
from uuid import uuid4
import connexion
import six
import traceback

from urllib3.exceptions import NewConnectionError

from nbi.swagger_server.models.delete_inter_nfvi_pop_connectivity_request import DeleteInterNfviPopConnectivityRequest  # noqa: E501
from nbi.swagger_server.models.inline_response200 import InlineResponse200  # noqa: E501
from nbi.swagger_server.models.inline_response201 import InlineResponse201
from nbi.swagger_server.models.inter_nfvi_pop_connectivity_request import InterNfviPopConnectivityRequest  # noqa: E501
from nbi.swagger_server.models.inter_nfvi_pop_connnectivity_id_list import InterNfviPopConnnectivityIdList
from nbi.swagger_server.models.inter_nfvi_pop_connnectivity_id import InterNfviPopConnnectivityId
from nbi.swagger_server.models.inter_nfvi_pop_network_segment_type import InterNfviPopNetworkSegmentType
from nbi.swagger_server.models.inter_nfvi_pop_connnectivity_id_list_inner import InterNfviPopConnnectivityIdListInner
from nbi.swagger_server import util
from nbi.swagger_server.models.http_errors import *

from orchestrator import orch, ro

from sqlalchemy.exc import IntegrityError
from nbi.nbi_server import db_session
from db.db_models import *
from sbi.openstack_connector import *

import logging


def collect_mtp_cloud_network_resource_information():  # noqa: E501
    """Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

    Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity # noqa: E501


    :rtype: InlineResponse200
    """
    # print(connexion.request.remote_addr)
    logger = logging.getLogger('cttc_mtp.nbi.retrieve_resources')
    try:
        nfvi_pops, lls_list = orch.retrieve_mtp_resources()
        logger.info('Retrieved the abstracted resources')
        with open('body_output.dat', 'r+') as file:
            content = file.read()
            file.seek(0, 0)
            file.write("\n{} --> \n{}\n{}".format(str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')),
                                                  str(InlineResponse200(nfvi_pops=nfvi_pops,
                                                                        logical_link_inter_nfvi_pops=lls_list)),
                                                  content))
        return InlineResponse200(nfvi_pops=nfvi_pops, logical_link_inter_nfvi_pops=lls_list), 200
    except(TypeError, ConnectionError, NewConnectionError) as e:
        return error400(str(e))


def collect_mtp_federated_cloud_network_resource_information():  # noqa: E501
    """Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation

    Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation # noqa: E501


    :rtype: InlineResponse200
    """
    logger = logging.getLogger('cttc_mtp.nbi.retrieve_federated_resources')
    try:
        nfvi_pops, lls_list = orch.retrieve_mtp_federated_resources()
        logger.info('Retrieved the abstracted federated resources')
        with open('body_output.dat', 'r+') as file:
            content = file.read()
            file.seek(0, 0)
            file.write("\n{} --> \n{}\n{}".format(str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')),
                                                  str(InlineResponse200(nfvi_pops=nfvi_pops,
                                                                        logical_link_inter_nfvi_pops=lls_list)),
                                                  content))
        return InlineResponse200(nfvi_pops=nfvi_pops, logical_link_inter_nfvi_pops=lls_list), 200
    except(TypeError, ConnectionError, NewConnectionError) as e:
        return error400(str(e))


def create_inter_nfvi_po_p_connectivity(body):  # noqa: E501
    """Create inter-NFVI-PoP connectivity

     # noqa: E501

    :param body: Create inter-NfviPop Connectivity
    :type body: dict | bytes

    :rtype: List[object]
    """
    logger = logging.getLogger('cttc_mtp.nbi.create_connectivity')
    # print(connexion.request.headers, type(connexion.request.headers))
    if connexion.request.is_json:
        body = InterNfviPopConnectivityRequest.from_dict(connexion.request.get_json())
        with open('body_input.dat', 'r+') as file:
            content = file.read()
            file.seek(0, 0)
            file.write("\n{} --> \n{}\n{}".format(str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')),
                                                  str(body),
                                                  content))
    try:
        connectivity_list = orch.create_connectivity(body)
        logger.info('Inter-NFVIPoP connectivity created')
        return connectivity_list, 201
    except(AttributeError, KeyError, IndexError, TypeError) as e:
        print(traceback.format_exc(), type(e).__name__, e.args)
        logger.error(e)
        return error400('Error during the internfvipop connectivity id creation. Check log')


def delete_inter_nfvi_po_p_connectivity(body):  # noqa: E501
    """Delete inter-NFVI-PoP connectivity

     # noqa: E501

    :param body: Delete inter-NfviPop Connectivity
    :type body: dict | bytes

    :rtype: None
    """
    logger = logging.getLogger('cttc_mtp.delete_connectivity')
    if connexion.request.is_json:
        body = DeleteInterNfviPopConnectivityRequest.from_dict(connexion.request.get_json())
        with open('body_input.dat', 'r+') as file:
            content = file.read()
            file.seek(0, 0)
            file.write("\n{} --> \n{}\n{}".format(str(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')),
                                                  str(body),
                                                  content))
    try:
        orch.delete_connectivity(body)
        return None, 204
    except(ValueError, AttributeError, KeyError) as e:
        print(traceback.format_exc(), type(e).__name__, e.args)
        logger.error(e)
        return error400('Error during the internfvipop connectivity id deletion. Check log')
