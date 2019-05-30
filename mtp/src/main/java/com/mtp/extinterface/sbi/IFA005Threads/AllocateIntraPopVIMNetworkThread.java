/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMRequest;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResult;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimNetworkResourcesApi;

/**
 *
 * @author efabuba
 */
public class AllocateIntraPopVIMNetworkThread extends Thread {
    private DomainElem dominfo;
    private IntraPoPAllocateVIMRequest request;
    public AllocateIntraPopVIMNetworkThread (DomainElem val, IntraPoPAllocateVIMRequest req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
        
        IntraPoPAllocateVIMReply allvimrep;

        //SET THE REQUEST IFA005 segmentType ATTRIBUTE PROVIDED BY THE WIM 
        //UNCOMMENT AFTER THE API BUGS ARE FIXED !!!!
        //request.getNetworkRequest().setTypeNetworkData().setSegmentType(request.getWimnetlist().get(0).setSegmentType(segmentType));
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);
        AllocateNetworkResult networkresponse;
         
        if (dominfo.getName().contains("OpenStack") == true) {
            AllocateNetworkResult initrep;
            AllocateNetworkRequest initreq = new  AllocateNetworkRequest();
            initreq.setNetworkResourceName("net-" + request.getIntrapopreq().getNetworkResourceName());
            initreq.setNetworkResourceType("network");
            try {

                initrep = api.vIMallocateNetwork(initreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside vIMallocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;

            }
            
            
            request.getIntrapopreq().setNetworkResourceName("subnet-" + request.getIntrapopreq().getNetworkResourceName());
            request.getIntrapopreq().getTypeSubnetData().setNetworkId(initrep.getSubnetData().getNetworkId());
            try {

                networkresponse = api.vIMallocateNetwork(request.getIntrapopreq());
            } catch (ApiException e) {
                System.out.println("ApiException inside vIMallocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;

            }

        } else {
            try {

                networkresponse = api.vIMallocateNetwork(request.getIntrapopreq());
            } catch (ApiException e) {
                System.out.println("ApiException inside vIMallocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;

            }
        }
        //send event
        allvimrep = new IntraPoPAllocateVIMReply(request.getReqid(), request.getServid(), request.getNfvipopid(),
        request.getVimid(), request.getIntrapopreq(), networkresponse);
        SingletonEventBus.getBus().post(allvimrep);
    }
}
