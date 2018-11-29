/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.resourcemanagement;

import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.common.objects.PendingNetworkAllocateRequest;
import com.mtp.common.objects.PendingNetworkTerminateRequest;
import com.mtp.common.objects.VIMAbstractElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryOutcome;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReq;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstance;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstanceOutcome;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryOutcome;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReply;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReq;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstance;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstanceOutcome;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstance;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryOutcome;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstance;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstanceOutcome;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryOutcome;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReply;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReq;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReq;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
import com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author user
 */
public class ResouceOrchestration {

    private HashMap<Long, PendingNetworkAllocateRequest> pendingNetAllocReq;
    private HashMap<Long, PendingNetworkTerminateRequest> pendingNetTermReq;

    public ResouceOrchestration() {
        pendingNetAllocReq = new HashMap();
        pendingNetTermReq = new HashMap();
    }

    @Subscribe
    public void handle_E2EComputeAllocateInstance(E2EComputeAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle ComputeAllocateDBQuery Event");
        //TODO: orchestrate allocate operation

        //Get the AllocateComputeRequest instance and put it inside ComputeAllocateVIMReq
        AllocateComputeRequest computeReq = allinst.getCompAllocElem().getAllocateRequest().getRequest();

//  FROM io.swagger.client.model TO com.mtp.extinterface.nbi.swagger.model
////Get the AllocateComputeRequest instance and put it inside ComputeAllocateVIMReq
//        com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest computeReq = allinst.getCompAllocElem().getAllocateRequest().getE2EVIMElem();
//        // 
//        List<CreateComputeResourceReservationRequestAntiAffinityConstraint> constraints = (List<CreateComputeResourceReservationRequestAntiAffinityConstraint>) (Object) computeReq.getAffinityOrAntiAffinityConstraints();
//        //
//        String locationConstraints = computeReq.getLocationConstraints();
//        //
//        String reservationId = computeReq.getReservationId();
//        //
//        String computeFlavourId = computeReq.getComputeFlavourId();
//        //
//        String resourceGroupId = computeReq.getResourceGroupId();
//        //
//        String vcImageId = computeReq.getVcImageId();
//        //
//        String computeName = computeReq.getComputeName();
//        //
//        Object metadata = computeReq.getMetadata();
//        //
//        List<AllocateComputeRequestInterfaceData> interfaceData = (List<AllocateComputeRequestInterfaceData>) (Object) computeReq.getInterfaceData();
//        //    
//        AllocateComputeRequestUserData userData = (AllocateComputeRequestUserData) (Object) computeReq.getUserData();
//
//        AllocateComputeRequest allocateComputeReq = new AllocateComputeRequest();
//        allocateComputeReq.setLocationConstraints(locationConstraints);
//        allocateComputeReq.setReservationId(reservationId);
//        allocateComputeReq.setInterfaceData(interfaceData);
//        allocateComputeReq.setAffinityOrAntiAffinityConstraints(constraints);
//        allocateComputeReq.setUserData(userData);
//        allocateComputeReq.setComputeFlavourId(computeFlavourId);
//        allocateComputeReq.setResourceGroupId(resourceGroupId);
//        allocateComputeReq.setMetadata(metadata);
//        allocateComputeReq.setVcImageId(vcImageId);
//        allocateComputeReq.setComputeName(computeName);
        ComputeAllocateVIMReq allvimreq = new ComputeAllocateVIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomid(),
                computeReq, allinst.getNfvipopid());

//        ComputeAllocateVIMReq allvimreq = new ComputeAllocateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId(),
//                new AllocateComputeRequest());
        System.out.println("ResouceOrchestration --> Post ComputeAllocateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_ComputeAllocateVIMReply(ComputeAllocateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_ComputeAllocateVIMReply Event");
       



//        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                vimElem, allvimrep.getOutcome(), allvimrep.getComputeservid(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.isOutcome(),
                allvimrep.getComputereply(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        
        System.out.println("ResouceOrchestration --> Post ComputeAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
//        E2EComputeAllocateInstanceOutcome allinstout = new E2EComputeAllocateInstanceOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());

    }

    @Subscribe
    public void handle_E2ENetworkAllocateInstance(E2ENetworkAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle ComputeAllocateDBQuery Event");
//        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqId(),
//                allinst.getServId(), 3,
//                new AllocateParameters());

        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getLogicalPathId(), allinst.getNetworkRequest(), allinst.getWimdomlist(),
                allinst.getInterdomainLinks(), allinst.getWanLinks(), allinst.getWimPopList(),
                allinst.getWimNetworkType());

        System.out.println("ResouceOrchestration --> Post NetworkAllocateWIMReq Event");

        PendingNetworkAllocateRequest pendingRequest = new PendingNetworkAllocateRequest(allinst.getReqid(), allinst.getServid(), allinst.getLogicalPathId(),
                allinst.getNetworkRequest(), allinst.getWimdomlist(), allinst.getVimdomlist(),
                allinst.getInterdomainLinks(), allinst.getIntraPopLinks(), allwimreq.getWanLinks(),
                allinst.getWimPopList(), allinst.getVimPopList(), allinst.getWimNetworkType(), allinst.getVimNetworkType());

        pendingNetAllocReq.put(allwimreq.getLogicalPathId(), pendingRequest);

        SingletonEventBus.getBus().post(allwimreq);
    }

    @Subscribe
    public void handle_NetworkAllocateWIMReply(NetworkAllocateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkAllocateWIMReply Event");
        //retrieve request from pendingreq
        PendingNetworkAllocateRequest pendingel = pendingNetAllocReq.get(allwimrep.getLogicallinkid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getReqid());
            //TODO: Send API response
            return;
        }
        if (allwimrep.isOutcome()) {
            pendingel.setWimnetlist(allwimrep.getWimnetlist());

            NetworkAllocateVIMReq allvimreq = new NetworkAllocateVIMReq(pendingel.getReqid(), pendingel.getServid(),
                    pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                    pendingel.getIntraPopLinks(), pendingel.getVimPopList(), pendingel.getVimNetworkType(), allwimrep.getWimnetlist());

            System.out.println("ResouceOrchestration --> Post NetworkAllocateVIMReq Event");
            SingletonEventBus.getBus().post(allvimreq);
        } else {

            NetworkAllocateDBQueryOutcome dbres = new NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
                    false, pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                    pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
                    pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), null, null);
          

            //remove request from pending
            pendingNetAllocReq.remove(allwimrep.getLogicallinkid());
        }

    }

    @Subscribe
    public void handle_NetworkAllocateVIMReply(NetworkAllocateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_NetworkAllocateVIMReply Event");
        PendingNetworkAllocateRequest pendingel = pendingNetAllocReq.get(allvimrep.getLogicallinkid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allvimrep.getReqid());
            //TODO: Send API response
            return;
        }

        NetworkAllocateDBQueryOutcome dbres = new NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
                allvimrep.isOutcome(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
                pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), pendingel.getWimnetlist(), allvimrep.getVimnetlist());
        


//remove request from pending
        pendingNetAllocReq.remove(allvimrep.getLogicallinkid());
        
         System.out.println("ResouceOrchestration --> Post NetworkAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dbres);
        
        

    }

    @Subscribe
    public void handle_E2EComputeTerminateInstance(E2EComputeTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2EComputeTerminateInstance Event");
        //TODO: orchestrate terminate operation
        ComputeTerminateVIMReq allvimreq = new ComputeTerminateVIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomlist(), allinst.getPoplist(), allinst.getComputeTermElem());
//        ComputeTerminateVIMReq allvimreq = new ComputeTerminateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId());
        System.out.println("ResouceOrchestration --> Post ComputeTerminateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_ComputeTerminateVIMReply(ComputeTerminateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle ComputeTerminateVIMReply Event");
      
        //Remove negative response from the list of computeId 
        for (int i = 0; i < allvimrep.getOutcomeList().size(); i++) {
            if (allvimrep.getOutcomeList().get(i) == false) {
                allvimrep.getComputeIdList().remove(i);
                allvimrep.getDomIdList().remove(i);
                allvimrep.getErrorcodeList().remove(i);
                allvimrep.getErrormsgList().remove(i);
                allvimrep.getOutcomeList().remove(i);
                allvimrep.getPoplist().remove(i);

            }
        }

        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomIdList(), allvimrep.getOutcomeList(),
                allvimrep.getErrorcodeList(), allvimrep.getErrormsgList(), allvimrep.getPoplist(),
                allvimrep.getComputeIdList());

//        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());
//        
        System.out.println("ResouceOrchestration --> Post ComputeTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
    }

    @Subscribe
    public void handle_E2ENetworkTerminateInstance(E2ENetworkTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2ENetworkTerminateInstance Event");
        //TODO: orchestrate terminate operation

        PendingNetworkTerminateRequest pendingReq = new PendingNetworkTerminateRequest(allinst.getReqid(), allinst.getServid(),
                allinst.getWimdomlistMap(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(),
                allinst.getIntraPopLinksMap(), allinst.getWanLinksMap(), allinst.getWimPopListMap(), allinst.getVimPopListMap(),
                allinst.getWimNetworkTypeMap(), allinst.getVimNetworkTypeMap(), allinst.getWanResourceIdListMap(), allinst.getNetServIdList(), allinst.getLocicalPathList());
        pendingNetTermReq.put(allinst.getReqid(), pendingReq);

        NetworkTerminateVIMReq allvimreq = new NetworkTerminateVIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(),
                allinst.getIntraPopLinksMap(), allinst.getVimPopListMap(), allinst.getVimNetworkTypeMap(), allinst.getLocicalPathList());

        
        System.out.println("ResouceOrchestration --> Post NetworkTerminateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_NetworkTerminateWIMReply(NetworkTerminateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkTerminateWIMReply Event");

        PendingNetworkTerminateRequest pendingel = pendingNetTermReq.get(allwimrep.getReqid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getReqid());
            //TODO: Send API response
            return;
        }

        for (int i = 0; i < pendingel.getLocicalLinkList().size(); i++) {
            boolean fail = false;
            for (int j = 0; (fail == false) && (j < pendingel.getLocicalLinkList().size()); j++) {

                fail = allwimrep.getOutcomeListMap().get(i).get(j);

            }
            if (fail == false) {
                pendingel.getLocicalLinkList().remove(i);
                pendingel.getInterdomainLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getVimNetworkTypeMap().remove(i);
                pendingel.getVimPopListMap().remove(i);
                pendingel.getVimdomlistMap().remove(i);
                pendingel.getWanLinksMap().remove(i);
                pendingel.getWimNetworkTypeMap().remove(i);
                pendingel.getWimPopListMap().remove(i);
                pendingel.getWimdomlistMap().remove(i);
                pendingel.getNetServIdList().remove(i);
            }

        }

       
        
         NetworkTerminateDBQueryOutcome dboutcome = new NetworkTerminateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(), 
         pendingel.getWimdomlistMap(), pendingel.getVimdomlistMap(), pendingel.getInterdomainLinksMap(), pendingel.getIntraPopLinksMap(), 
         pendingel.getWanLinksMap(), pendingel.getWimPopListMap(), pendingel.getVimPopListMap(), pendingel.getWimNetworkTypeMap(), 
         pendingel.getVimNetworkTypeMap(),pendingel.getWanResourceIdListMap() , pendingel.getNetServIdList(), pendingel.getLocicalLinkList());
        System.out.println("ResouceOrchestration --> Post NetworkTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);

        
        
        
        //MOVE BELOW EVENT INSIDE DATABASEDRIVER handle_NetworkTerminateDbQueryOutcome
//        E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome(allwimrep.getReqid(),
//                allwimrep.getServid(), new VIMAbstractElem(), allwimrep.getOutcome());
//         E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome();
//        System.out.println("ResouceOrchestration --> Post E2ENetworkAllocateInstanceOutcome Event");
//        SingletonEventBus.getBus().post(allinstout);
    }

    @Subscribe
    public void handle_NetworkTerminateVIMReply(NetworkTerminateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkterminateVIMReply Event");

        PendingNetworkTerminateRequest pendingel = pendingNetTermReq.get(allvimrep.getReqid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allvimrep.getReqid());
            //TODO: Send API response
            return;
        }

        for (int i = 0; i < pendingel.getLocicalLinkList().size(); i++) {
            boolean fail = false;
            for (int j = 0; (fail == false) && (j < pendingel.getLocicalLinkList().size()); j++) {

                fail = allvimrep.getOutcomeListMap().get(i).get(j);

            }
            if (fail == false) {
                pendingel.getLocicalLinkList().remove(i);
                pendingel.getInterdomainLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getVimNetworkTypeMap().remove(i);
                pendingel.getVimPopListMap().remove(i);
                pendingel.getVimdomlistMap().remove(i);
                pendingel.getWanLinksMap().remove(i);
                pendingel.getWimNetworkTypeMap().remove(i);
                pendingel.getWimPopListMap().remove(i);
                pendingel.getWimdomlistMap().remove(i);
                pendingel.getNetServIdList().remove(i);
            }

        }

        NetworkTerminateWIMReq allwimreq = new NetworkTerminateWIMReq(allvimrep.getReqid(),
                allvimrep.getServid(), pendingel.getWimdomlistMap(), pendingel.getInterdomainLinksMap(), pendingel.getWanLinksMap(),
                pendingel.getWimPopListMap(), pendingel.getWimNetworkTypeMap(), pendingel.getWanResourceIdListMap());

        System.out.println("ResouceOrchestration --> Post NetworkTerminateWIMReq Event");
        SingletonEventBus.getBus().post(allwimreq);

    }
}
