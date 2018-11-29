package com.mtp.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import com.mtp.extinterface.nbi.swagger.model.InlineResponse201;
import com.mtp.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;



import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/abstract-network-resources")
@Api(description = "the abstract-network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-23T15:51:40.129Z")
public class AbstractNetworkResourcesApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static Map<String, Integer> allocationreq = new ConcurrentHashMap();
    private static Map<String, Integer> allocationcurrentreq = new ConcurrentHashMap();
    private static Map<String, List<InlineResponse201>> allocationreply = new ConcurrentHashMap();
    private static long reqid = 0;

    public AbstractNetworkResourcesApi() {
        //reqid = 0;

    }

    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create inter-NFVI-PoP connectivity", notes = "", response = InlineResponse201.class, tags={ "abstractResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful operation", response = InlineResponse201.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void createInterNfviPoPConnectivity(@Suspended final AsyncResponse ar, @Valid InterNfviPopConnectivityRequest body) {
       //return Response.ok().entity("magic!").build();
        long servid = -1;
        System.out.println("createInterNfviPoPConnectivity ----> allocate abstract network request suspended");
        System.out.println("createInterNfviPoPConnectivity ----> Calling post");
        reqid++;
        System.out.println("createInterNfviPoPConnectivity ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        allocationreq.put(Long.toString(reqid), body.getLogicalLinkPathList().size());
        allocationcurrentreq.put(Long.toString(reqid), 0);
        allocationreply.put(Long.toString(reqid), new ArrayList());
        for (int i =0; i < body.getMetaData().size(); i++) {
            if (body.getMetaData().get(i).getKey().compareTo("ServiceId")== 0) {
                servid = Long.parseLong(body.getMetaData().get(i).getValue());
            }
        }
        //   E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid,10, params);
        E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid, servid, body);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Consumes({ "application/json" })
    @ApiOperation(value = "Delete inter-NFVI-PoP connectivity", notes = "", response = Void.class, tags={ "abstractResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void deleteInterNfviPoPConnectivity(@Suspended final AsyncResponse ar, @QueryParam("networkId") @NotNull    List<String> networkId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("terminateNetwork ----> allocate compute request suspended");
        System.out.println("terminateNetwork ----> Calling post");
        reqid++;
        System.out.println("terminateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        E2ENetworkTerminateRequest request = new E2ENetworkTerminateRequest();
        request.setReqid(reqid);
        request.setServid(-1);
        
        request.setNetServIdList(networkId);
        SingletonEventBus.getBus().post(request);
    }
  

    
    
        ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_E2ENetworkAllocateReply(E2ENetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();
        int val = allocationcurrentreq.get(Long.toString(ev.getReqid()));
        int num = allocationreq.get(Long.toString(ev.getReqid()));
        List<InlineResponse201> replylist = allocationreply.get(Long.toString(ev.getReqid()));
        InlineResponse201 el = new InlineResponse201();
        el.setInterNfviPopConnnectivityId(ev.getVn().getNetworkResourceId());
        el.setInterNfviPopNetworkSegmentType(ev.getVn().getNetworkType());
        replylist.add(el);
        val += 1; //increment the value 
        if (val != num) {
            System.out.println("allocateNetwork ----> Wait other response");
            allocationcurrentreq.put(Long.toString(ev.getReqid()), val);
            allocationreply.put(Long.toString(ev.getReqid()), replylist);
            return;
        }
        allocationreq.remove(Long.toString(ev.getReqid()));
        allocationcurrentreq.remove(Long.toString(ev.getReqid()));
        allocationreply.remove(Long.toString(ev.getReqid()));
        //send response
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("allocateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(replylist, MediaType.APPLICATION_JSON).build();
        System.out.println("allocateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2ENetworkTerminateReply(E2ENetworkTerminateReply ev) throws InterruptedException {
        System.out.println("terminateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("terminateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok().build();
        System.out.println("terminateNetwork ----> response ok");
        asyncResp.resume(resp);
    }
}
