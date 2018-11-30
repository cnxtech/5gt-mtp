
# IFA005 plugins
This folder contains the plugin to controls the compute (e.g. Datacentres) and network domains (both radio and transport).
The folder files contains the json scripts to generate the template code for the communication with the MTP core via [Swagger](https://editor.swagger.io/)
The folder plugins contains the code od the different plugins. 
The plugins are divided according the domain topology in:

## 1. WIM 
It contains the plugins for transport domain. In R1 release  the following plugin are developed:
- **_5G Crosshaul WIM Plugin_** (plugin that controls the transport nodes developed in [5G-Crosshaul EU project](http://5g-crosshaul.eu/) ). Released with [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
- **_5G SDN WIM Plugin_** (plugin to SDN control based domains via [ONOS](https://onosproject.org/) ). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license. 

## 2. VIM
It contains the plugins for datacenter domain. In R1 release the following plugin are developed:
- **_Openstack Plugin_** (tested on [Openstack queen release](https://releases.openstack.org/queens/)). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_XenServer Plugin_** (tested on [XenServer version 6.5](https://xenserver.org/open-source-virtualization-download.html)). Released with [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
## 3. MEC
It contains the plugin for MEC  domains (feature that will be developed in the next R2 release)

## 4. RADIO
It contains the plugin for radio domains (feature that will be developed in the next R2 release)
