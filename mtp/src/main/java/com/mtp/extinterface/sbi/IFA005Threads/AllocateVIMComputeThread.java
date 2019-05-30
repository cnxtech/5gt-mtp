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
import com.mtp.extinterface.nbi.swagger.model.VIMAllocateComputeRequest;
import com.mtp.extinterface.nbi.swagger.model.VIMVirtualCompute;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimComputeResourcesApi;


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
        
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        VimComputeResourcesApi api = new VimComputeResourcesApi(capi);
        VirtualCompute computeresponse = new VirtualCompute();
        VIMVirtualCompute vimcomputeresp;
        VIMAllocateComputeRequest vimallocreq = new VIMAllocateComputeRequest();
        ComputeAllocateVIMReply allvimrep;
        
        //Convert AllocateRequest to VIMAllocateRequest
        vimallocreq.setAffinityOrAntiAffinityConstraints(request.getComputereq().getAffinityOrAntiAffinityConstraints());
        vimallocreq.setComputeFlavourId(request.getComputereq().getComputeFlavourId());
        vimallocreq.setComputeName(request.getComputereq().getComputeName());
        vimallocreq.setInterfaceData(request.getComputereq().getInterfaceData());
        vimallocreq.setLocationConstraints(request.getComputereq().getLocationConstraints());
        vimallocreq.setMetadata(request.getComputereq().getMetadata());
        vimallocreq.setReservationId(request.getComputereq().getReservationId());
        vimallocreq.setResourceGroupId(request.getComputereq().getResourceGroupId());
        vimallocreq.setUserData(request.getComputereq().getUserData());
        vimallocreq.setVcImageId(request.getComputereq().getVcImageId());
        
        try {
            //Filter nfviPopComputeInformationRequest = null;
            vimcomputeresp = api.allocateCompute(vimallocreq);
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
        
        //Copy VIMComputeResponse with VirtualCompute
        computeresponse.setAccelerationCapability(vimcomputeresp.getAccelerationCapability());
        computeresponse.setComputeId(vimcomputeresp.getComputeId());
        computeresponse.setComputeName(vimcomputeresp.getComputeName());
        computeresponse.setFlavourId(vimcomputeresp.getFlavourId());
        computeresponse.setHostId(vimcomputeresp.getHostId());
        computeresponse.setOperationalState(vimcomputeresp.getOperationalState());
        computeresponse.setVcImageId(vimcomputeresp.getVcImageId());
        computeresponse.setVirtualCpu(vimcomputeresp.getVirtualCpu());
        computeresponse.setVirtualDisks(vimcomputeresp.getVirtualDisks());
        computeresponse.setVirtualMemory(vimcomputeresp.getVirtualMemory());
        computeresponse.setVirtualNetworkInterface(vimcomputeresp.getVirtualNetworkInterface());
        computeresponse.setZoneId(vimcomputeresp.getZoneId());
        computeresponse.setMeCappID(request.getComputereq().getMeCAppDId());
        //send event
         allvimrep = new ComputeAllocateVIMReply( request.getReqid(),request.getServid(), 
                        request.getDomid(), true, 0, null, computeresponse, 
                        request.getNfvipopid(),request.getComputereq());
        SingletonEventBus.getBus().post(allvimrep);
    }
}
