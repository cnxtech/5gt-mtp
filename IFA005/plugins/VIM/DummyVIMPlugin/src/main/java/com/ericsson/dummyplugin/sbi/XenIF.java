/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneRequest;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionRequest;
import com.ericsson.dummyplugin.events.abstraction.Parsedomainlist;
import com.ericsson.dummyplugin.events.abstraction.StartServer;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateReply;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateRequest;
import com.ericsson.dummyplugin.events.allocate.ComputeOperateReply;
import com.ericsson.dummyplugin.events.allocate.ComputeOperateRequest;
import com.ericsson.dummyplugin.events.allocate.VirtualNetworkAllocateReply;
import com.ericsson.dummyplugin.events.allocate.VirtualNetworkAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateReply;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateRequest;
import com.ericsson.dummyplugin.events.terminate.VirtualNetworkTerminateReply;
import com.ericsson.dummyplugin.events.terminate.VirtualNetworkTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResult;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResultSubnetData;
import com.ericsson.dummyplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.MetaDataInner;
import com.ericsson.dummyplugin.nbi.swagger.model.NfviPop;
import com.ericsson.dummyplugin.nbi.swagger.model.ResourceZone;
import com.ericsson.dummyplugin.nbi.swagger.model.SubnetData;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMAllocateComputeRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMVirtualCompute;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeVirtualMemory;
import com.ericsson.dummyplugin.sbi.objects.ComputeResource;
import com.ericsson.dummyplugin.sbi.objects.NFVIPop;
import com.ericsson.dummyplugin.sbi.objects.NetworkService;
import com.ericsson.dummyplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class XenIF {
    private List<NFVIPop> poplist;
    private List<Zone> zonelist;
    private HashMap<String, ComputeResource> compreslist;
    private HashMap<String, NetworkService> netservlist;
    private HashMap<String, AllocateNetworkResult> intranetservlist;
    private HashMap<String, VIMVirtualCompute> compservlist;
    private int netservcounter;
    private int intranetservcounter;
    private int compservcounter;
    
    public XenIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        compreslist = new HashMap();
        netservlist = new HashMap();
        compservlist = new HashMap();
        intranetservlist = new HashMap();
        netservcounter = 1;
        compservcounter = 1;
        intranetservcounter = 1000;
    }
    
    
    //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("CrossHaulIF ---> Parse domlist from xml file");
        //TODO: Parse list of domain from xml file
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        poplist = xmldom.getPoplist();
        zonelist = xmldom.getZonelist();
        compreslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    

    @Subscribe
    public void handle_ComputeAllocateRequest(ComputeAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate compute service request" );
        VIMAllocateComputeRequest compreq = servallreq.getRequest();
        String servid = new String("");
        List<MetaDataInner> inputdata = servallreq.getRequest().getMetadata();
        for (int i = 0; i < inputdata.size(); i++) {
            if(inputdata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = inputdata.get(i).getValue();
            }
        }
        
         
        VIMVirtualCompute compel = new VIMVirtualCompute();
        compel.setComputeId(Integer.toString(compservcounter));
        compel.setComputeName("XenCompService" + Integer.toString(netservcounter));
        compel.setFlavourId(compreq.getComputeFlavourId());
        compel.setHostId("XENHost");
        compel.setOperationalState("enable");
        compel.setVcImageId(compreq.getVcImageId());
        compel.setVirtualDisks("1");
        ComputeResource cpuel = new ComputeResource();
        ComputeResource memel = new ComputeResource();
        //retrieve virtual CPU from resources
        for (ComputeResource value : compreslist.values()) {
            if (value.getType().compareTo("cpu") == 0) {
                cpuel = value;
            } else {
                memel = value;
            }
                
        }
        VirtualComputeVirtualCpu virtualCpu = new VirtualComputeVirtualCpu();
        virtualCpu.setCpuArchitecture(cpuel.getArchitecture());
        virtualCpu.setCpuClock(new BigDecimal(cpuel.getCpuclock()));
        virtualCpu.setNumVirtualCpu(Integer.parseInt(cpuel.getNumvcpu())); //TODO: Check in POC
        virtualCpu.setVirtualCpuOversubscriptionPolicy(cpuel.getSubscr_policy());
        compel.setVirtualCpu(virtualCpu);
        VirtualComputeVirtualMemory virtualMemory = new VirtualComputeVirtualMemory();
        
        virtualMemory.setNumaEnabled(memel.isNumaSupported());
        virtualMemory.setVirtualMemOversubscriptionPolicy(memel.getSubscr_policy());
        virtualMemory.setVirtualMemSize(new BigDecimal(memel.getMemsize()));
        compel.setVirtualMemory(virtualMemory);
        //insert in service table
        compservlist.put(Integer.toString(compservcounter), compel);  
        
        compservcounter++;
        ComputeAllocateReply servallrep = new ComputeAllocateReply(servallreq.getReqId(),
                    compel);
        System.out.println("XenIF ---> post ComputeAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_ComputeTerminateRequest(ComputeTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate compute service request" );
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getRequest().size(); i++) {
            String key = servtermreq.getRequest().get(i);
            VIMVirtualCompute servel = compservlist.get(key);
            if (servel != null) {
                compservlist.remove(key);
                resplist.add(key);
            }
        }
        
        ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), resplist);
        System.out.println("XenIF ---> post ComputeTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
    
     @Subscribe
    public void handle_ComputeOperateRequest(ComputeOperateRequest compreq) {
        System.out.println("XenIF ---> compute operate request" );
 
            
        VIMVirtualCompute servel = compservlist.get(compreq.getRequest().getComputeId());
         if (servel != null) {
            servel.setOperationalState(compreq.getRequest().getComputeOperation());
            ComputeOperateReply comprep = new ComputeOperateReply(compreq.getReqid(), servel);
            System.out.println("XenIF ---> post ComputeOperateReply");
            SingletonEventBus.getBus().post(comprep);
         } else {
            ComputeOperateReply comprep = new ComputeOperateReply(compreq.getReqid(), new VIMVirtualCompute());
            System.out.println("XenIF ---> post ComputeTerminateReply");
            SingletonEventBus.getBus().post(comprep);
         }
   }
   
    
    @Subscribe
    public void handle_VirtualNetworkAllocateRequest(VirtualNetworkAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate network service request" );
        
        
        List<MetaDataInner> inputdata = servallreq.getE2ewimelem().getMetadata();
        
        String servid = new String("-1"), nettype = new String(""), netinfo = new String("");
        
        for (int i = 0; i < inputdata.size(); i++) {
            if(inputdata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = inputdata.get(i).getValue();
            } else if (inputdata.get(i).getKey().compareTo("NetworkType") == 0) {
                nettype = inputdata.get(i).getValue();
            } else if (inputdata.get(i).getKey().compareTo("SegmentType") == 0) {
                netinfo = inputdata.get(i).getValue();
            }
        }
        if (netinfo == "") {
            //intraPoP API
            AllocateNetworkResult resp = new AllocateNetworkResult();
            AllocateNetworkResultSubnetData networkSubnetData = new AllocateNetworkResultSubnetData();
            SubnetData inputsubnetdata = servallreq.getE2ewimelem().getTypeSubnetData();
            networkSubnetData.setAddressPool(inputsubnetdata.getAddressPool());
            networkSubnetData.setCidr(inputsubnetdata.getCidr());
            networkSubnetData.setGatewayIp(inputsubnetdata.getGatewayIp());
            networkSubnetData.setIpVersion(inputsubnetdata.getIpVersion());
            networkSubnetData.setIsDhcpEnabled(inputsubnetdata.isIsDhcpEnabled());
            networkSubnetData.setMetadata(inputsubnetdata.getMetadata());
            networkSubnetData.setNetworkId(inputsubnetdata.getNetworkId());
            networkSubnetData.setOperationalState("enabled");
            networkSubnetData.setResourceId(Integer.toString(intranetservcounter));
            resp.setSubnetData(networkSubnetData);
            intranetservlist.put(Integer.toString(intranetservcounter), resp);
            intranetservcounter++;
            VirtualNetworkAllocateReply servallrep = new VirtualNetworkAllocateReply(servallreq.getReqid(),
                    resp);
            System.out.println("XenIF ---> post VirtualNetworkAllocateReply");
            SingletonEventBus.getBus().post(servallrep); 
        } else {
            NetworkService el = new NetworkService(servid, nettype, netinfo);
            netservlist.put(Integer.toString(netservcounter), el);

            AllocateNetworkResult resp = new AllocateNetworkResult();
            AllocateNetworkResultNetworkData networkData = new AllocateNetworkResultNetworkData();
            //put id in the reply
            networkData.setNetworkResourceId(Integer.toString(netservcounter));
            networkData.setNetworkResourceName("XenNetService" + Integer.toString(netservcounter));
            networkData.bandwidth(BigDecimal.ONE);
            networkData.setSegmentType(nettype);
            networkData.setSegmentType(netinfo);
            networkData.setIsShared(Boolean.TRUE);
            networkData.setOperationalState("enabled");
            resp.setNetworkData(networkData);
            netservcounter++;
            VirtualNetworkAllocateReply servallrep = new VirtualNetworkAllocateReply(servallreq.getReqid(),
                    resp);
            System.out.println("XenIF ---> post VirtualNetworkAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
        }
    }
    
    @Subscribe
    public void handle_VirtualNetworkTerminateRequest(VirtualNetworkTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate network service request" );
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getE2EWIMElem().size(); i++) {
            String key = servtermreq.getE2EWIMElem().get(i);
            if (Integer.valueOf(key) >= 1000) {
                AllocateNetworkResult intraservel = intranetservlist.get(key);
                if (intraservel != null) {
                    intranetservlist.remove(key);
                    resplist.add(key);
                }
            } else {
                NetworkService servel = netservlist.get(key);
                if (servel != null) {
                    netservlist.remove(key);
                    resplist.add(key);
                }
            }
        }
        
        VirtualNetworkTerminateReply servtermrep = new VirtualNetworkTerminateReply(servtermreq.getReqId(),resplist);
        System.out.println("XenIF ---> post VirtualNetworkTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }    
    
    
    @Subscribe
    public void handle_ComputeCapacityRequest(ComputeCapacityRequest compcapreq) {
        System.out.println("XenIF ---> retrieve compute resources capacity for resid = " + compcapreq.getCapacityRequest().getComputeResourceTypeId());
        
        ComputeResource el = compreslist.get(compcapreq.getCapacityRequest().getComputeResourceTypeId());
        
        if (el == null) {
            System.out.println("XenIF ---> no compute resource found for resid = " + compcapreq.getCapacityRequest().getComputeResourceTypeId());
            ComputeCapacityReply compcaprep = new ComputeCapacityReply(compcapreq.getReqId(),new CapacityInformation());
            System.out.println("XenIF ---> post NetworkCapacityReply");
            SingletonEventBus.getBus().post(compcaprep);
            return;
        }
        
        CapacityInformation compcapel = new CapacityInformation();
        
        compcapel.setAllocatedCapacity(el.getAllocated());
        compcapel.setAvailableCapacity(el.getAvailable());
        compcapel.setReservedCapacity(el.getReserved());
        compcapel.setTotalCapacity(el.getTotal());
        
        
        ComputeCapacityReply compcaprep = new ComputeCapacityReply(compcapreq.getReqId(),compcapel);
        System.out.println("XenIF ---> post NetworkCapacityReply");
        SingletonEventBus.getBus().post(compcaprep);
    }
    
    @Subscribe
    public void handle_ComputeResourceInformationRequest(ComputeResourceInformationRequest compresreq) {
        System.out.println("XenIF ---> retrieve network resources");
        List<VirtualComputeResourceInformation> compresreqlist = new ArrayList(); 
        
        for (ComputeResource value : compreslist.values()) {
            VirtualComputeResourceInformation el = new VirtualComputeResourceInformation();
            el.setAccelerationCapability(value.getAcc_capability());
            el.setComputeResourceTypeId(value.getId());
            if (value.getType().compareTo("cpu") == 0 ) {
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                virtualCPU.setCpuArchitecture(value.getArchitecture());
                virtualCPU.setCpuClock(new BigDecimal(value.getCpuclock()));
                virtualCPU.setNumVirtualCpu(new BigDecimal(value.getNumvcpu()));
                virtualCPU.setVirtualCpuOversubscriptionPolicy(value.getSubscr_policy());
                virtualCPU.setVirtualCpuPinningSupported(value.isPinningSupported());
                el.setVirtualCPU(virtualCPU);
            } else if (value.getType().compareTo("mem") == 0) {
                VirtualComputeResourceInformationVirtualMemory virtualMemory = new VirtualComputeResourceInformationVirtualMemory();
                virtualMemory.setNumaSupported(value.isNumaSupported());
                virtualMemory.setVirtualMemOversubscriptionPolicy(value.getSubscr_policy());
                virtualMemory.setVirtualMemSize(new BigDecimal(value.getMemsize()));
                el.setVirtualMemory(virtualMemory);
            }
            compresreqlist.add(el);
        }
        ComputeResourceInformationReply netzonerep = new ComputeResourceInformationReply(compresreq.getReqId(),compresreqlist);
        System.out.println("XenIF ---> post NetworkResourceInformationReply");
        SingletonEventBus.getBus().post(netzonerep);
    }
    
    @Subscribe
    public void handle_ComputeZoneRequest(ComputeZoneRequest netzonereq) {
        System.out.println("XenIF ---> retrieve zone list");
        List<ResourceZone> zonereplist = new ArrayList(); 
        for (int i = 0; i < zonelist.size(); i++) {
            ResourceZone el = new ResourceZone();
            el.setNfviPopId(zonelist.get(i).getPop());
            el.setZoneId(zonelist.get(i).getId());
            el.setZoneName(zonelist.get(i).getName());
            el.setZoneProperty(zonelist.get(i).getProperty());
            el.setZoneState(zonelist.get(i).getStatus());
            zonereplist.add(el);
        }
        ComputeZoneReply netzonerep = new ComputeZoneReply(netzonereq.getReqid(),zonereplist);
        System.out.println("XenIF ---> post NetworkZoneReply");
        SingletonEventBus.getBus().post(netzonerep);
    }
    
    @Subscribe
    public void handle_NfviPopAbstractionRequest(NfviPopAbstractionRequest nfvipopreq) {
        System.out.println("XenIF ---> retrieve nfvipop list");
        List<NfviPop> popreplist = new ArrayList(); 
        for (int i = 0; i < poplist.size(); i++) {
            NfviPop el = new NfviPop();
            el.setGeographicalLocationInfo(poplist.get(i).getLocation());
            el.setNetworkConnectivityEndpoint(poplist.get(i).getEndpoints());
            el.setNfviPopId(poplist.get(i).getPop());
            el.setVimId(poplist.get(i).getVim());
            popreplist.add(el);
        }
        NfviPopAbstractionReply nfvipoprep = new NfviPopAbstractionReply(nfvipopreq.getReqId(),popreplist);
        System.out.println("XenIF ---> post NfviPopAbstractionReply");
        SingletonEventBus.getBus().post(nfvipoprep);
        
    }
}
