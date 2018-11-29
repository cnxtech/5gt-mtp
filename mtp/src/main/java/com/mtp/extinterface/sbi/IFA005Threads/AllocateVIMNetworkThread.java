/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.mtp.extinterface.nbi.swagger.model.MetaDataInner;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VirtualisedNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AllocateVIMNetworkThread extends Thread {
    private List<DomainElem> dominfo;
    private NetworkAllocateVIMReq request;
    public AllocateVIMNetworkThread (List<DomainElem> val, NetworkAllocateVIMReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
        
        NetworkAllocateVIMReply allvimrep;
        ArrayList<VirtualNetwork> vimnetlist = new ArrayList();
 
        //SET THE REQUEST IFA005 segmentType ATTRIBUTE PROVIDED BY THE WIM 
        //UNCOMMENT AFTER THE API BUGS ARE FIXED !!!!
        //request.getNetworkRequest().setTypeNetworkData().setSegmentType(request.getWimnetlist().get(0).setSegmentType(segmentType));
        
        

        for (int i = 0; i < request.getVimdomlist().size(); i++) {
            String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/" + dominfo.get(i).getName();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            VirtualisedNetworkResourcesApi api = new VirtualisedNetworkResourcesApi(capi);

            AllocateNetworkResult networkresponse; 
            List<MetaDataInner> datalist = new ArrayList();
            AllocateNetworkRequest inputreq = new AllocateNetworkRequest();
            String servid = new String("");
            //Retrieve service id from metadata    
            for (int j = 0; j < request.getNetworkRequest().getMetaData().size(); j++) {
                if (request.getNetworkRequest().getMetaData().get(j).getKey().compareTo("ServiceId") == 0) {
                    servid = request.getNetworkRequest().getMetaData().get(j).getValue();
                }
            }
            inputreq.setNetworkResourceType("network");
            MetaDataInner data = new MetaDataInner();
            data.setKey("ServiceId");
            data.setValue(servid);
            datalist.add(data);
            data = new MetaDataInner();
            data.setKey("NetworkType");
            data.setValue(request.getWimnetlist().get(i).getNetworkType());
            datalist.add(data);
            data = new MetaDataInner();
            data.setKey("SegmentType");
            data.setValue(request.getWimnetlist().get(i).getSegmentType());
            datalist.add(data);
            inputreq.setMetadata(datalist);
            try {
 
                networkresponse = api.vIMallocateNetwork(inputreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside allocateNetwork VIM().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allvimrep = new NetworkAllocateVIMReply(request.getReqid(), request.getServid(),
                        false, 0, "", null, request.getLogicalPathId());
                allvimrep.setErrorcode(e.getCode());
                allvimrep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allvimrep);
                return;

            }
            //Copy network data in virtual network
            VirtualNetwork netel = new VirtualNetwork();
            
            netel.setBandwidth(networkresponse.getNetworkData().getBandwidth());
            netel.setIsShared(networkresponse.getNetworkData().isIsShared());
            netel.setNetworkPort(networkresponse.getNetworkData().getNetworkPort());
            netel.setNetworkQoS(networkresponse.getNetworkData().getNetworkQoS());
            netel.setNetworkResourceId(networkresponse.getNetworkData().getNetworkResourceId());
            netel.setNetworkResourceName(networkresponse.getNetworkData().getNetworkResourceName());
            netel.setNetworkType(networkresponse.getNetworkData().getNetworkType());
            netel.setOperationalState(networkresponse.getNetworkData().getOperationalState());
            netel.setSegmentType(networkresponse.getNetworkData().getSegmentType());
            netel.setSharingCriteria(networkresponse.getNetworkData().getSharingCriteria());
            netel.setSubnet(networkresponse.getNetworkData().getSubnet());
            vimnetlist.add(netel);
        }
        //send event
        allvimrep = new NetworkAllocateVIMReply(request.getReqid(), request.getServid(),
                true, 0, "", vimnetlist, request.getLogicalPathId());
        SingletonEventBus.getBus().post(allvimrep);
    }
}
