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

from six.moves.configparser import RawConfigParser

from urllib3 import HTTPConnectionPool

import pa.swagger_client
from pa.swagger_client.rest import ApiException
from pa.swagger_client.models import CompRouteInput, CompRouteInputInterWanLinks, CompRouteInputNetwLinkQoS, \
    CompRouteInputAbsWanTopo, CompRouteInputNodes, CompRouteInputEdges, CompRouteInputQosCons
from pprint import pprint
import logging


class PaClient(object):
    def __init__(self, filename=None):
        self.filename = filename
        self.logger = logging.getLogger('cttc_mtp.pa.pa_client')

        self.conf = pa.swagger_client.Configuration()
        # conf.host = 'http://10.1.16.46:8088'
        pa_config = RawConfigParser()
        pa_config.read(filename)
        pa_server = pa_config.get("DEFAULT", "pa_default")
        self.logger.info("'{}' choose as PA".format(pa_server))
        self.conf.host = 'http://{}:{}'.format(pa_config.get(pa_server, "ip"), pa_config.get(pa_server, "port"))
        self.pa_id = int(pa_config.get(pa_server, "id"))
        self.k_paths = int(pa_config.get(pa_server, "kpaths"))

    def compute_path(self,
                     inter_nfvipop_connectivity_id,
                     inter_wan_links,
                     list_of_topology,
                     src_pe,
                     dst_pe,
                     required_bw,
                     required_delay):
        api_instance = pa.swagger_client.InterNfviPopCompRouteApi(pa.swagger_client.ApiClient(configuration=self.conf))
        # body.pa_id = inter_nfvipop_connectivity_id
        # pprint(list_of_topology)
        try:
            body = CompRouteInput(pa_id=self.pa_id,
                                  k_paths=self.k_paths,
                                  src_pe_id=src_pe,
                                  dst_pe_id=dst_pe,
                                  inter_wan_links=[CompRouteInputInterWanLinks(
                                      a_wim_id=link['aWimId'],
                                      z_wim_id=link['zWimId'],
                                      a_pe_id=link['aPEId'],
                                      z_pe_id=link['zPEId'],
                                      a_link_id=int(link['aLinkId']),
                                      z_link_id=int(link['zLinkId']),
                                      netw_link_qo_s=CompRouteInputNetwLinkQoS(link_cost="linkCost",
                                                                               link_cost_value=10,
                                                                               link_delay="linkDelay",
                                                                               link_delay_value=1,
                                                                               link_avail_bw="linkAvailableBw",
                                                                               link_avail_bw_value=20)
                                  ) for link in inter_wan_links],
                                  abs_wan_topo=[CompRouteInputAbsWanTopo(wim_id=topology['wim_id'],
                                                                         nodes=[
                                                                             CompRouteInputNodes(node_id=node['nodeId'])
                                                                         for node in topology['topology']['nodes']],
                                                                         edges=[
                                                                             CompRouteInputEdges(a_node_id=edge['source'],
                                                                                                 z_node_id=edge['target'],
                                                                                                 a_link_id=int(edge['localIfid']),
                                                                                                 z_link_id=int(edge['remoteIfid']),
                                                                                                 netw_link_qo_s=
                                                                                                 CompRouteInputNetwLinkQoS(
                                                                                                     link_cost="linkCost",
                                                                                                     link_cost_value=10,
                                                                                                     link_delay="linkDelay",
                                                                                                     link_delay_value=float(edge.get('delay', 0)),
                                                                                                     link_avail_bw="linkAvailBw",
                                                                                                     link_avail_bw_value=float(edge['unreservBw'])),
                                                                                                 network_layer=edge['switchingCap'])
                                                                         for edge in topology['topology']['edges']])
                                                for topology in list_of_topology],
                                  qos_cons=CompRouteInputQosCons(bandwidth_cons="bandwidthCons",
                                                                 bandwidth_cons_value=required_bw,
                                                                 delay_cons="delayCons",
                                                                 delay_cons_value=required_delay)

                                  )
            self.logger.info("Body of the PA request done!")
            api_response = api_instance.comp_route_inter_nfvi_pop(inter_nfvi_connectivity_id=inter_nfvipop_connectivity_id,
                                                                  params=body,
                                                                  async_req=True)
            # print(api_response, type(api_response))
            response = api_response.get()
            return response
        except(ApiException, HTTPConnectionPool)as e:
            self.logger.error("Exception when calling DefaultApi->exposures_get: %s\n" % str(e))
            raise KeyError("Error in PA client!")
