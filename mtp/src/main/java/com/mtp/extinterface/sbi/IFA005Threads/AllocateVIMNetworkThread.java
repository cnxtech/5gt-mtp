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
import io.swagger.client.api.VimNetworkResourcesApi;
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
            //String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/" + dominfo.get(i).getName();
            String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);

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
            //retrieve wan link. 
            //As each link is has to be associated to two VIM domain take , 
            //just take the quotient of i
            
            if (dominfo.get(i).getName().contains("OpenStack") == true ) {
                int wimpos = i / 2;
                //when sending ip floating to desctination VIM, the floating IP of transport should be swutched
                inputreq.setNetworkResourceType("network");
                MetaDataInner data = new MetaDataInner();
                data.setKey("ServiceId");
                data.setValue(servid);
                datalist.add(data);
                data = new MetaDataInner();
                data.setKey("NetworkType");
                data.setValue(request.getWimnetlist().get(wimpos).getNetworkType());
                datalist.add(data);
                data = new MetaDataInner();
                data.setKey("SegmentType");
                data.setValue(request.getWimnetlist().get(wimpos).getSegmentType());
                datalist.add(data);
                //retrieve ip floting ip send the ip in the reverse order to egress domain
                String[] ipsplit = request.getWimnetlist().get(wimpos).getSegmentType().split(";");
                int ingrdom = i%2; //if 0 we are sending info to the ingress domain, if 1 to the egress domain 
                String segtype;
                if (ingrdom == 0) {
                    segtype = ipsplit[0] + ";" + ipsplit[1];
                } else {
                    segtype = ipsplit[1] + ";" + ipsplit[0];
                } 
                data.setKey("SegmentType");
                data.setValue(segtype);
                datalist.add(data);
                inputreq.setMetadata(datalist);
            } else {
                int wimpos = i / 2;
                inputreq.setNetworkResourceType("network");
                MetaDataInner data = new MetaDataInner();
                data.setKey("ServiceId");
                data.setValue(servid);
                datalist.add(data);
                data = new MetaDataInner();
                data.setKey("NetworkType");
                data.setValue(request.getWimnetlist().get(wimpos).getNetworkType());
                datalist.add(data);
                data = new MetaDataInner();
                data.setKey("SegmentType");
                data.setValue(request.getWimnetlist().get(wimpos).getSegmentType());
                datalist.add(data);
                inputreq.setMetadata(datalist);
            }
            //Add 
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
