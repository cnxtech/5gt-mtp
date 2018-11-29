package com.mtp.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.events.abstraction.Advertisement.NetworkCapacityReply;
import com.mtp.events.abstraction.Advertisement.NetworkCapacityRequest;
import com.mtp.events.abstraction.Advertisement.NfviPopAbstractionReply;
import com.mtp.events.abstraction.Advertisement.NfviPopAbstractionRequest;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import com.mtp.extinterface.nbi.swagger.model.CapacityInformation;
import com.mtp.extinterface.nbi.swagger.model.Filter;
import com.mtp.extinterface.nbi.swagger.model.NfviPop;
import com.mtp.extinterface.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.ArrayList;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/network-resources")
@Api(description = "the network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class NetworkResourcesApi {

    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public NetworkResourcesApi() {
        //reqid = 0;

    }

    @POST
    @ManagedAsync
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualNetwork.class, tags = {"virtualisedNetworkResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = VirtualNetwork.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
        ,
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class)})
//    public Response allocateNetwork(@Valid AllocateParameters params) {
//        return Response.ok().entity("magic!").build();
//    }
    public void allocateNetwork(@Suspended final AsyncResponse ar/*, @Valid AllocateParameters params*/) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("allocateNetwork ----> allocate compute request suspended");
        System.out.println("allocateNetwork ----> Calling post");
        reqid++;
        System.out.println("allocateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        //TODO: Fix how to obtain the servid
        // Get the servId from resourceGroupId attribute of IFA005 allocateNetworkRequest
        //long servId = Long.valueOf(params.getResourceGroupId());
        //   E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid,10, params);
        //E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid, servId, params);
        //SingletonEventBus.getBus().post(request);
    }

    @GET
    @ManagedAsync
    @Path("/nfvi-pop-network-information")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags = {"virtualisedNetworkResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryNFVIPoPNetworkInformation(@Valid Filter nfviPopNetworkInformationRequest) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryNFVIPoPNetworkInformation(@Suspended final AsyncResponse ar, @QueryParam("filter") String nfviPopNetworkInformationRequest) throws InterruptedException {
        //return Response.ok().entity("magic!").build();
        //suspended.add(ar);
        System.out.println("queryNFVIPoPNetworkInformation ----> nfvi-pop-network-information request suspended");
        System.out.println("queryNFVIPoPNetworkInformation ----> Calling post");
        reqid++;
        System.out.println("queryNFVIPoPNetworkInformation ----> reqid = " + Long.toString(reqid));
        System.out.println("queryNFVIPoPNetworkInformation ----> filter = " + nfviPopNetworkInformationRequest);
        suspended.put(Long.toString(reqid), ar);
        SingletonEventBus.getBus().post(new NfviPopAbstractionRequest(reqid, nfviPopNetworkInformationRequest, false, true));
    }

    @GET
    @ManagedAsync
    @Path("/capacities")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags = {"virtualisedNetworkResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryNetworkCapacity(@Valid QueryNetworkCapacityRequest queryNetworkCapacityRequest) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryNetworkCapacity(@Suspended final AsyncResponse ar, @Valid QueryNetworkCapacityRequest queryNetworkCapacityRequest) throws InterruptedException {
        System.out.println("queryNetworkCapacity ----> capacities request suspended");
        System.out.println("queryNetworkCapacity ----> Calling post");
        reqid++;
        System.out.println("queryNetworkCapacity ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        NetworkCapacityRequest request = new NetworkCapacityRequest(reqid);
        request.setCapacityRequest(queryNetworkCapacityRequest);
        SingletonEventBus.getBus().post(request);
        //return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualNetwork.class, responseContainer = "List", tags = {"virtualisedNetworkResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Element containing information about the virtual network resource(s) matching the filter. The cardinality can be 0 if no matching network resources exist.", response = VirtualNetwork.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    public Response queryNetworks(@Valid Filter networkQueryFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @ManagedAsync
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags = {"virtualisedNetworkResources"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = String.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response terminateNetworks(@Valid List<NetworkIds> ids) {
//        return Response.ok().entity("magic!").build();
//    }
    public void terminateNetworks(@Suspended final AsyncResponse ar, @QueryParam("idlist") String listid) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("terminateNetwork ----> allocate compute request suspended");
        System.out.println("terminateNetwork ----> Calling post");
        reqid++;
        System.out.println("terminateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        E2ENetworkTerminateRequest request = new E2ENetworkTerminateRequest();
        request.setReqid(reqid);
        //request.setServid(10);
        
        List<String> ids = new ArrayList();
        String[] tokens = listid.split(",");
        request.setNetServIdList(ids);
        SingletonEventBus.getBus().post(request);
    }

    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_NfviPopAbstractionReply(NfviPopAbstractionReply ev) throws InterruptedException {
        if (!ev.getNetworkFlag()) {
            System.out.println("queryNFVIPoPNetworkInformation ----> not handle request");
            return;
        }
        System.out.println("queryNFVIPoPNetworkInformation ----> handle testrest event");
        //AsyncResponse asyncResp = suspended.take();
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNFVIPoPNetworkInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryNFVIPoPNetworkInformation ----> request deblocked");
        ArrayList<NfviPop> poplist;
        poplist = ev.getnfvipoplist();
        Response resp;
        if (poplist.isEmpty()) {
            resp = Response.ok(new NfviPop(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(poplist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("queryNFVIPoPNetworkInformation ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_NetworkCapacityReply(NetworkCapacityReply ev) throws InterruptedException {
        System.out.println("queryNetworkCapacity ----> handle reply event");
        //AsyncResponse asyncResp = suspended.take();
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNetworkCapacity ----> reqid = " + ev.getReqId());
        System.out.println("queryNetworkCapacity ----> request deblocked");
        //ArrayList<com.mtp.extinterface.nbi.swagger.model.NfviPop> poplist;
        CapacityInformation capinfo = ev.getCapacityInfo();
        Response resp;
        resp = Response.ok(capinfo, MediaType.APPLICATION_JSON).build();
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2ENetworkAllocateReply(E2ENetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateNetwork ----> reqid = " + ev.getServid());
        System.out.println("allocateNetwork ----> request deblocked");
        VirtualNetwork replist;
        if (ev.isOutcome()) {
            replist = ev.getVn();
        } else {
            replist = new VirtualNetwork();
        }
        Response resp;
        resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
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
        List<String> replist;
        replist = ev.getNetServIdList();
        Response resp;
        if (replist.isEmpty()) {
            resp = Response.ok(new String(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("terminateNetwork ----> response ok");
        asyncResp.resume(resp);
    }
}
