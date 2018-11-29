
# IFA005 plugins
This folder contains the plugin to controls the compute (e.g. Datacentres) and network domains (both radio and transport).
The plugins are divided according the domain topology in:

## 1. WIM 
It contains the plugins for transport domain. In R1 release  the following plugin are developed:
- 5G Crosshaul WIM Plugin (plugin that controls the transport nodes developed in [5G-Crosshaul EU project](http://5g-crosshaul.eu/) )
- 5G SDN WIM Plugin (plugin to SDN control based domains via [ONOS](https://onosproject.org/) ) 

## 2. VIM
It contains the plugins for datacenter domain. In R1 release the following plugin are developed:
- Openstack Plugin (tested on [Openstack queen release](https://releases.openstack.org/queens/))
- XenServer Plugin (tested on [XenServer version 6.5](https://xenserver.org/open-source-virtualization-download.html))
## 3. MEC
It contains the plugin for MEC  domains (feature that will be developed in the next R2 release)

## 4. RADIO
It contains the plugin for radio domains (feature that will be developed in the next R2 release)
