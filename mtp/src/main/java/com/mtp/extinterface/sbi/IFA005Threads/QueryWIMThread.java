/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.common.objects.MapResources;
import com.mtp.common.objects.NetworkResElem;
import com.mtp.events.abstraction.Creation.WIMResAbstractionEvent;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import com.mtp.extinterface.nbi.swagger.model.CapacityInformation;
import com.mtp.extinterface.nbi.swagger.model.Filter;
import com.mtp.extinterface.nbi.swagger.model.InlineResponse200;
import com.mtp.extinterface.nbi.swagger.model.LogicalLinkAttributes;
import com.mtp.extinterface.nbi.swagger.model.NfviPop;
import com.mtp.extinterface.nbi.swagger.model.ResourceZone;
import com.mtp.extinterface.nbi.swagger.model.VirtualLinks;
import com.mtp.extinterface.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class QueryWIMThread extends Thread {
    private DomainElem dominfo;
    public  QueryWIMThread (DomainElem val) {
        dominfo = val;
    }
    
    @Override
    public void run() {
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort()+ "/" + dominfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        
        WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);
        
     
        List<NfviPop> poplist = new ArrayList(); 
        List<ResourceZone> zonelist= new ArrayList();
        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        List<LogicalLinkAttributes> linkinfolist = new ArrayList();
        CapacityInformation caprep;
        
        WIMResAbstractionEvent ev = new WIMResAbstractionEvent(dominfo.getId());
        InlineResponse200 resp = new InlineResponse200();
        //Retrieve nfvipop query, no filter
        try {
            resp = api.collectWimAbstractedInformation();
        } catch (ApiException e) {
            System.out.println("ApiException inside collectWimAbstractedInformation()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        
        //build nfvipop
            ResourceZone zonel = new ResourceZone();
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo(resp.getGateways().get(0).getGatewayAttributes().getGeographicalLocationInfo());
            String connedp = new String("");
        for (int i = 0; i < resp.getGateways().size(); i++) {            
            if (i != 0) {
                //new edgepoint to insert
                connedp += ";";
            }
            String edpel = resp.getGateways().get(i).getGatewayAttributes().getGatewayId();
            connedp += edpel;
        }
        popel.setNetworkConnectivityEndpoint(connedp);
        popel.setNfviPopId(resp.getGateways().get(0).getGatewayAttributes().getWimId());
        popel.setVimId(resp.getGateways().get(0).getGatewayAttributes().getWimId());
        poplist.add(popel);
        //Create fake zone for the network
        zonel.setNfviPopId(popel.getNfviPopId());
        zonel.setZoneId(popel.getNfviPopId());
        zonel.setZoneName("WIMname");
        zonel.setZoneProperty("WIMProperty");
        zonel.setZoneState("enabled");
        zonelist.add(zonel);

        //set poplist to event
        ev.setPopList(poplist);
        //add zoneid to event
        ev.setZoneList(zonelist);
        
        //create resources for each virtual links
        for (int i = 0; i < resp.getVirtualLinks().size(); i++) {
            VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
            LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
            AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
            List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
            
            //add networkinfo
            VirtualLinksInnerVirtualLink link = resp.getVirtualLinks().get(i).getVirtualLink();
            qos.setQosName("delay");
            qos.setQosValue(link.getNetworkQoS().getLinkDelayValue().toString());
            qoslist.add(qos);
            resnetel.setNetworkQoS(qoslist);
            resnetel.setBandwidth(link.getTotalBandwidth()); //minimum bw required is one
            resnetel.setNetworkResourceTypeId(link.getVirtualLinkId());
            resnetel.setNetworkType(link.getNetworkLayer());
            netinfolist.add(resnetel);
            
            //add linkinfo
            resInfo.setSrcGwIpAddress(link.getSrcGwId());
            resInfo.setDstGwIpAddress(link.getDstGwId());
            resInfo.setLocalLinkId(link.getSrcLinkId());
            resInfo.setRemoteLinkId(link.getDstLinkId());
            resInfo.setLogicalLinkId(link.getVirtualLinkId());
            linkinfolist.add(resInfo);            
        }
            
        for (int i = 0; i < netinfolist.size(); i++) {

            caprep = new CapacityInformation();
            caprep.setAllocatedCapacity("0");
            caprep.setAvailableCapacity(resp.getVirtualLinks().get(i).getVirtualLink().getAvailableBandwidth().toString());
            caprep.setReservedCapacity("0");
            caprep.setTotalCapacity(resp.getVirtualLinks().get(i).getVirtualLink().getTotalBandwidth().toString());
            //Add element to compute list in the event
            NetworkResElem netel = new NetworkResElem();
            netel.setNetworkElem(netinfolist.get(i));
            netel.setCapacityElem(caprep);
            netel.setLinkinfo(linkinfolist.get(i));
            ev.setNetworkResElem(netel);
            //create element in map
            MapResources mapel = new MapResources(Long.valueOf(zonelist.get(0).getNfviPopId()),
                    Long.valueOf(zonelist.get(0).getZoneId()),
                    Long.valueOf(netinfolist.get(i).getNetworkResourceTypeId()));
            ev.setNetworkMapElem(mapel);
        }//Loop on virtual network resources            
        //send event
        SingletonEventBus.getBus().post(ev);
    }
}
