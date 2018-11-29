/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi;

import com.ericsson.xenplugin.SingletonEventBus;
import com.ericsson.xenplugin.events.abstraction.ComputeCapacityReply;
import com.ericsson.xenplugin.events.abstraction.ComputeCapacityRequest;
import com.ericsson.xenplugin.events.abstraction.ComputeResourceInformationReply;
import com.ericsson.xenplugin.events.abstraction.ComputeResourceInformationRequest;
import com.ericsson.xenplugin.events.abstraction.ComputeZoneReply;
import com.ericsson.xenplugin.events.abstraction.ComputeZoneRequest;
import com.ericsson.xenplugin.events.abstraction.NfviPopAbstractionReply;
import com.ericsson.xenplugin.events.abstraction.NfviPopAbstractionRequest;
import com.ericsson.xenplugin.events.abstraction.Parsedomainlist;
import com.ericsson.xenplugin.events.abstraction.StartServer;
import com.ericsson.xenplugin.events.allocate.ComputeAllocateReply;
import com.ericsson.xenplugin.events.allocate.ComputeAllocateRequest;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateReply;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateRequest;
import com.ericsson.xenplugin.events.terminate.ComputeTerminateReply;
import com.ericsson.xenplugin.events.terminate.ComputeTerminateRequest;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateReply;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateRequest;
import com.ericsson.xenplugin.sbi.objects.ComputeResource;
import com.ericsson.xenplugin.sbi.objects.ComputeService;
import com.ericsson.xenplugin.sbi.objects.NFVIPop;
import com.ericsson.xenplugin.sbi.objects.NetworkService;
import com.ericsson.xenplugin.sbi.objects.XenService;
import com.ericsson.xenplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.mtp.extinterface.nbi.swagger.model.CapacityInformation;
import com.mtp.extinterface.nbi.swagger.model.MetaDataInner;
import com.mtp.extinterface.nbi.swagger.model.NfviPop;
import com.mtp.extinterface.nbi.swagger.model.ResourceZone;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
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
    private HashMap<String, XenService> xenreslist;
    private HashMap<String, NetworkService> netservlist;
    private HashMap<String, ComputeService> compservlist;
    private int netservcounter;
    private int compservcounter;
    
    public XenIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        compreslist = new HashMap();
        xenreslist = new HashMap();
        netservlist = new HashMap();
        netservcounter = 1;
        compservcounter = 1;
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
        xenreslist = xmldom.getXeninfolist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    

    @Subscribe
    public void handle_ComputeAllocateRequest(ComputeAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate compute service request" );
        AllocateComputeRequest compreq = servallreq.getRequest();
        String servid = new String("");
        List<MetaDataInner> inputdata = servallreq.getRequest().getMetadata();
        for (int i = 0; i < inputdata.size(); i++) {
            if(inputdata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = inputdata.get(i).getValue();
            }
        }
        //TODO: For R2 retrieve the virtual network created and remove oit from DC
        
        ComputeService el =  new ComputeService();
        //TODO Add Xen info and perform command, wait repsonse
        
        compservlist.put(Integer.toString(compservcounter), el);   
        VirtualCompute compel = new VirtualCompute();
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
            ComputeService servel = compservlist.get(key);
            if (servel != null) {
                // TODO: Send terminate VM to  command to XHAUL Control 
                compservlist.remove(key);
                resplist.add(key);
            }
            resplist.add(key);
        }
        
        ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), resplist);
        System.out.println("XenIF ---> post ComputeTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
   
    
    @Subscribe
    public void handle_VirtualNetworkAllocateRequest(VirtualNetworkAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate network service request" );
        
        //XXX: As XEN DC uses the default virtual switch no virtual network are created.
        // For R2 additional switch can be created
        //retrieve ServiceId, networktype and segment id from metadata
        
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
    
    @Subscribe
    public void handle_VirtualNetworkTerminateRequest(VirtualNetworkTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate network service request" );
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getE2EWIMElem().size(); i++) {
            String key = servtermreq.getE2EWIMElem().get(i);
            NetworkService servel = netservlist.get(key);
            if (servel != null) {
                //XXX: As XEN DC uses the default virtual switch no virtual network are created.
                // For R2 additional switch can be deleted
                //Send the stringid as reply ServiceId, networktype and segment id from metadata
                netservlist.remove(key);
                resplist.add(key);
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
