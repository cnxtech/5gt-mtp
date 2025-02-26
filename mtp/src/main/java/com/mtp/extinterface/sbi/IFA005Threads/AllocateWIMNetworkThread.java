/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.common.objects.NetworkEndpoints;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.mtp.extinterface.nbi.swagger.model.AllocateParameters;
import com.mtp.extinterface.nbi.swagger.model.AllocateReply;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AllocateWIMNetworkThread extends Thread {
   // private DomainElem dominfo;
    private List<DomainElem> dominfo;
    private NetworkAllocateWIMReq request;
    private List<NetworkEndpoints> endpoints;
   
    
//    public AllocateWIMNetworkThread (DomainElem val, NetworkAllocateWIMReq req) {
//        dominfo = val;
//        request = req;
//    }
    
    
     public AllocateWIMNetworkThread (List<DomainElem> val, NetworkAllocateWIMReq req, List<NetworkEndpoints> endp) {
        dominfo = val;
        request = req;
        endpoints = endp;
    }
    
    
    @Override
    public void run() {

        NetworkAllocateWIMReply allwimrep;
        ArrayList<VirtualNetwork> wimnetlist = new ArrayList();

        for (int i = 0; i < request.getWimdomlist().size(); i++) {
            //String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/" + dominfo.get(i).getName();
            String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);
            
            AllocateParameters param = new AllocateParameters();
            param.setBandwidth(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqBandwidth());
            param.setDelay(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqLatency().toString());
            param.setEgressPointIPAddress(endpoints.get(i).getEgressIp());
            param.setEgressPointPortAddress(endpoints.get(i).getEgressPort());
            param.setIngressPointIPAddress(endpoints.get(i).getIngressIp());
            param.setIngressPointPortAddress(endpoints.get(i).getIngressPort());
            param.setMetadata(Long.toString(request.getServid()));            
            param.setSegmentType(request.getNetworkRequest().getInterNfviPopNetworkType());
            param.setWanLinkId(request.getWanLinks().get(i).toString());
            

            AllocateReply networkresponse; //TODO:fix with networkAllocateoutput in swagger
            try {
                //Filter nfviPopComputeInformationRequest = null;
                networkresponse = api.allocateNetwork(param);
            } catch (ApiException e) {
                System.out.println("ApiException inside allocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allwimrep = new NetworkAllocateWIMReply(request.getReqid(), request.getServid(),
                        false, 0, "", null, request.getLogicalPathId());
                allwimrep.setErrorcode(e.getCode());
                allwimrep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allwimrep);
                return;

            }
            
            VirtualNetwork netel= new VirtualNetwork();
            List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS = new ArrayList();
            
            netel.setNetworkResourceId(networkresponse.getNetworkId());
            netel.setSegmentType(networkresponse.getSegmentType());
            netel.setNetworkType(networkresponse.getNetworkType());
            netel.setIsShared(Boolean.FALSE);
            netel.setBandwidth(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqBandwidth());
            AllocateNetworkResultNetworkDataNetworkQoS qosel = new AllocateNetworkResultNetworkDataNetworkQoS();
            qosel.setQosName("delay");
            qosel.setQosValue(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqLatency().toString());
            networkQoS.add(qosel);
            netel.setNetworkQoS(networkQoS);
            netel.setOperationalState("enabled");
            wimnetlist.add(netel);
        }
        //send event
        allwimrep = new NetworkAllocateWIMReply(request.getReqid(), request.getServid(),
                true, 0, "", wimnetlist, request.getLogicalPathId());
        SingletonEventBus.getBus().post(allwimrep);
    }
}
