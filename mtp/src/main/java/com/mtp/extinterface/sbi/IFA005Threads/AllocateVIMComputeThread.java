/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReq;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VirtualisedComputeResourcesApi;


/**
 *
 * @author efabuba
 */
public class AllocateVIMComputeThread extends Thread {
    private DomainElem dominfo;
    private ComputeAllocateVIMReq request;
    public AllocateVIMComputeThread (DomainElem val, ComputeAllocateVIMReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        VirtualisedComputeResourcesApi api = new VirtualisedComputeResourcesApi(capi);
        
        VirtualCompute computeresponse;
        ComputeAllocateVIMReply allvimrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            computeresponse = api.allocateCompute(request.getComputereq());
        } catch (ApiException e) {
            System.out.println("ApiException inside allocateCompute()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            allvimrep = new ComputeAllocateVIMReply( request.getReqid(),request.getServid(), 
                            request.getDomid(), false, 0, "", null, request.getNfvipopid(), 
                            request.getComputereq());
            allvimrep.setErrorcode(e.getCode());
            allvimrep.setErrormsg(e.getMessage());
            SingletonEventBus.getBus().post(allvimrep);
            return;
        }
        //send event
         allvimrep = new ComputeAllocateVIMReply( request.getReqid(),request.getServid(), 
                        request.getDomid(), true, 0, null, computeresponse, 
                        request.getNfvipopid(),request.getComputereq());
        SingletonEventBus.getBus().post(allvimrep);
    }
}
