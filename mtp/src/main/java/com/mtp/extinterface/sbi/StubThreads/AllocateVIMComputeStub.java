/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.StubThreads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReq;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import io.swagger.client.ApiClient;
import io.swagger.client.api.VirtualisedComputeResourcesApi;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AllocateVIMComputeStub extends Thread {

    private DomainElem dominfo;
    private ComputeAllocateVIMReq request;

    public AllocateVIMComputeStub(DomainElem val, ComputeAllocateVIMReq req) {
        dominfo = val;
        request = req;
    }

    @Override
    public void run() {

        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
//        ApiClient capi = new ApiClient();
//        capi.setBasePath(basepath);
//        VirtualisedComputeResourcesApi api = new VirtualisedComputeResourcesApi(capi);

        VirtualCompute computeresponse = new VirtualCompute();
        ComputeAllocateVIMReply allvimrep;
        VirtualComputeVirtualCpu virtualCpu = new VirtualComputeVirtualCpu();
        VirtualComputeVirtualMemory virtualMem = new VirtualComputeVirtualMemory();
        List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();
        ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface interface_1 = new ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface();
        ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface interface_2 = new ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface();
        double clock = 2.2;
        long mem = 1024;
        //Virtual Cpu
        virtualCpu.setCpuArchitecture("x86_64");
        virtualCpu.setNumVirtualCpu(4);
        virtualCpu.setCpuClock(BigDecimal.valueOf(clock));
        //Virtual Memory
        virtualMem.setNumaEnabled(false);
        virtualMem.setVirtualMemSize(BigDecimal.valueOf(mem));

        //Virtual Network Interface 1
        interface_1.setBandwidth("1000");
        interface_1.setIpAddress("10.10.10.10");
        interface_1.setMacAddress("MAC_ADD_1");
        //Virtual Network Interface 2
        interface_1.setBandwidth("100");
        interface_1.setIpAddress("11.11.11.11");
        interface_1.setMacAddress("MAC_ADD_2");

        virtualNetworkInterface.add(interface_1);
        virtualNetworkInterface.add(interface_2);

        computeresponse.setComputeId(Long.toString(request.getReqid()));
        computeresponse.setFlavourId(request.getComputereq().getComputeFlavourId());
        computeresponse.setComputeName(request.getComputereq().getComputeName());
        computeresponse.setHostId("127.0.0.1");
        computeresponse.setOperationalState("enable");
        computeresponse.setVirtualCpu(virtualCpu);
        computeresponse.setVirtualMemory(virtualMem);
        computeresponse.setVirtualDisks("Disk1");
        computeresponse.setVirtualNetworkInterface(virtualNetworkInterface);
        computeresponse.setZoneId(request.getComputereq().getLocationConstraints());

        //send event
        allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                request.getDomid(), true, 0, null, computeresponse,request.getNfvipopid(), request.getComputereq());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true, request.getComputeservid(),request.getNfvipopid());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true, request.getComputeservid());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true);
        SingletonEventBus.getBus().post(allvimrep);
    }
}



