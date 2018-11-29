package com.mtp.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.events.abstraction.Advertisement.ComputeCapacityReply;
import com.mtp.events.abstraction.Advertisement.ComputeCapacityRequest;
import com.mtp.events.abstraction.Advertisement.ComputeResourceInformationReply;
import com.mtp.events.abstraction.Advertisement.ComputeResourceInformationRequest;
import com.mtp.events.abstraction.Advertisement.ComputeResourceZoneReply;
import com.mtp.events.abstraction.Advertisement.ComputeResourceZoneRequest;
import com.mtp.events.abstraction.Advertisement.NfviPopAbstractionReply;
import com.mtp.events.abstraction.Advertisement.NfviPopAbstractionRequest;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateReply;
import com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import com.mtp.extinterface.nbi.swagger.model.CapacityInformation;
import com.mtp.extinterface.nbi.swagger.model.Filter;
import com.mtp.extinterface.nbi.swagger.model.NfviPop;
import com.mtp.extinterface.nbi.swagger.model.QueryComputeCapacityRequest;
import com.mtp.extinterface.nbi.swagger.model.ResourceZone;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeFlavour;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/compute-resources")
@Api(description = "the compute-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class ComputeResourcesApi {

    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public ComputeResourcesApi() {
        //reqid = 0;

    }

    @POST
    @ManagedAsync
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualCompute.class, tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VirtualCompute.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
        ,
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)})
    //public Response allocateCompute(@Valid AllocateComputeRequest body) {
    //    return Response.ok().entity("magic!").build();
    //}
    public void allocateCompute(@Suspended final AsyncResponse ar, @Valid AllocateComputeRequest body) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("allocateCompute ----> allocate compute request suspended");
        System.out.println("allocateCompute ----> Calling post");
        reqid++;
        System.out.println("allocateCompute ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        E2EComputeAllocateRequest request = new E2EComputeAllocateRequest();
        request.setReqid(reqid);
        //TODO: Fix how to obtain the servid
         //Retrieve the ServID from the ResourceGroup attribute of the IF005 AllocateComputeRequest
         request.setServid(Long.valueOf(body.getResourceGroupId()));
        //request.setServqId(10);

        request.setRequest(body);
        SingletonEventBus.getBus().post(request);
    }

    @POST
    @Path("/flavours")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = String.class, tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = String.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
        ,
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class)})
    public Response createFlavour(@Valid VirtualComputeFlavour flavour) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/flavours/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = Void.class, tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "No output parameters", response = Void.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    public Response deleteFlavours(@PathParam("id") @ApiParam("Identifier of the Compute Flavour to be deleted.") String id) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @ManagedAsync
    @Path("/capacities")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryComputeCapacity(@Valid QueryComputeCapacityRequest queryComputeCapacityRequest) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryComputeCapacity(@Suspended final AsyncResponse ar, @Valid QueryComputeCapacityRequest queryComputeCapacityRequest) throws InterruptedException {
        System.out.println("queryNetworkCapacity ----> capacities request suspended");
        System.out.println("queryNetworkCapacity ----> Calling post");
        reqid++;
        System.out.println("queryNetworkCapacity ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeCapacityRequest request = new ComputeCapacityRequest(reqid);
        request.setCapacityRequest(queryComputeCapacityRequest);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @ManagedAsync
    @Path("/information")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualComputeResourceInformation.class, responseContainer = "List", tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Virtualised compute resource information in the VIM that satisfies the query condition.", response = VirtualComputeResourceInformation.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryComputeInformation(@Valid Filter informationQueryFilter) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryComputeInformation(@Suspended final AsyncResponse ar, @QueryParam("filter") String informationQueryFilter) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryComputeInformation ----> capacities request suspended");
        System.out.println("queryComputeInformation ----> Calling post");
        reqid++;
        System.out.println("queryComputeInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeResourceInformationRequest request = new ComputeResourceInformationRequest(reqid, informationQueryFilter);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @ManagedAsync
    @Path("/resource-zones")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = ResourceZone.class, responseContainer = "List", tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved about the Resource Zone. The cardinality can be 0 if no matching information exist.", response = ResourceZone.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryComputeResourceZone(@Valid Filter queryComputeResourceZoneRequest) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryComputeResourceZone(@Suspended final AsyncResponse ar, @QueryParam("filter") String queryComputeResourceZoneRequest) {
        //return Response.ok().entity("Not Implemented!").build();
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryComputeResourceZone ----> capacities request suspended");
        System.out.println("queryComputeResourceZone ----> Calling post");
        reqid++;
        System.out.println("queryComputeResourceZone ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeResourceZoneRequest request = new ComputeResourceZoneRequest(reqid, queryComputeResourceZoneRequest);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Path("/flavours")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualCompute.class, responseContainer = "List", tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualCompute.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    public Response queryFlavors(@Valid Filter computeQueryFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @ManagedAsync
    @Path("/nfvi-pop-compute-information")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response queryNFVIPoPComputeInformation(@Valid Filter nfviPopComputeInformationRequest) {
//        return Response.ok().entity("magic!").build();
//    }
    public void queryNFVIPoPComputeInformation(@Suspended final AsyncResponse ar, @QueryParam("filter") String nfviPopComputeInformationRequest) throws InterruptedException {
        System.out.println("queryNFVIPoPComputeInformation ----> nfvi-pop-compute-information request suspended");
        System.out.println("queryNFVIPoPComputeInformation ----> Calling post");
        reqid++;
        System.out.println("queryNFVIPoPComputeInformation ----> reqid = " + Long.toString(reqid));
        System.out.println("queryNFVIPoPComputeInformation ----> filter = " + nfviPopComputeInformationRequest);
        suspended.put(Long.toString(reqid), ar);
        SingletonEventBus.getBus().post(new NfviPopAbstractionRequest(reqid, nfviPopComputeInformationRequest, true, false));
    }

    @GET
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = VirtualCompute.class, responseContainer = "List", tags = {"virtualisedComputeResources",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualCompute.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    public Response queryResources(@Valid Filter computeQueryFilter) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @ManagedAsync
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags = {"virtualisedComputeResources"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = String.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
//    public Response terminateResources(@Valid List<ComputeIds> ids) {
//        return Response.ok().entity("magic!").build();
//    }
    public void terminateResources(@Suspended final AsyncResponse ar, @QueryParam("idlist") String listid) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("terminateCompute ----> allocate compute request suspended");
        System.out.println("terminateCompute ----> Calling post");
        reqid++;
        System.out.println("terminateCompute ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);

        List<String> ids = new ArrayList();
        String[] tokens = listid.split(",");
        for (int i = 0; i < tokens.length; i++) {
            String el = new String();
            el = tokens[i];
            ids.add(el);
        }
       
       
       long servId = -1;
       //E2EComputeTerminateRequest request = new E2EComputeTerminateRequest(reqid, servId, ids);
       //E2EComputeTerminateRequest request = new E2EComputeTerminateRequest(reqid, 10, ids); 
        
        //SingletonEventBus.getBus().post(request);
    }

    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_NfviPopAbstractionReply(NfviPopAbstractionReply ev) throws InterruptedException {
        if (!ev.getComputeFlag()) {
            System.out.println("queryNFVIPoPComputeInformation ----> not handle request");
            return;
        }
        System.out.println("queryNFVIPoPComputeInformation ----> handle nfvipop compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNFVIPoPComputeInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryNFVIPoPComputeInformation ----> request deblocked");
        ArrayList<NfviPop> poplist;
        poplist = ev.getnfvipoplist();
        Response resp;
        if (poplist.isEmpty()) {
            resp = Response.ok(new NfviPop(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(poplist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("queryNFVIPoPComputeInformation ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ComputeCapacityReply(ComputeCapacityReply ev) throws InterruptedException {
        System.out.println("queryNetworkCapacity ----> handle capacity compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNetworkCapacity ----> reqid = " + ev.getReqId());
        System.out.println("queryNetworkCapacity ----> request deblocked");
        CapacityInformation cap = ev.getCapacityInfo();
        Response resp;
        resp = Response.ok(cap, MediaType.APPLICATION_JSON).build();
        System.out.println("queryNetworkCapacity ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ComputeResourceInformationReply(ComputeResourceInformationReply ev) throws InterruptedException {

        System.out.println("queryComputeInformation ----> handle resource info compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryComputeInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryComputeInformation ----> request deblocked");
        ArrayList<VirtualComputeResourceInformation> resinfolist;
        resinfolist = ev.getnetresinfolist();
        Response resp;
        if (resinfolist.isEmpty()) {
            resp = Response.ok(new VirtualComputeResourceInformation(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(resinfolist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("queryComputeInformation ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ComputeResourceZoneReply(ComputeResourceZoneReply ev) throws InterruptedException {
        System.out.println("queryComputeResourceZone ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryComputeResourceZone ----> reqid = " + ev.getReqId());
        System.out.println("queryComputeResourceZone ----> request deblocked");
        ArrayList<ResourceZone> zonelist;
        zonelist = ev.getnetresinfolist();
        Response resp;
        if (zonelist.isEmpty()) {
            resp = Response.ok(new ResourceZone(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(zonelist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("queryComputeResourceZone ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2EComputeAllocateReply(E2EComputeAllocateReply ev) throws InterruptedException {
        System.out.println("allocateCompute ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateCompute ----> reqid = " + ev.getReqid());
        System.out.println("allocateCompute ----> request deblocked");
        VirtualCompute replist;
        if (ev.isOutcome()) {
            replist = ev.getComputereply();
        } else {
            replist = new VirtualCompute();
        }
        Response resp;
        resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        System.out.println("terminateCompute ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2EComputeTerminateReply(E2EComputeTerminateReply ev) throws InterruptedException {
        System.out.println("terminateCompute ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateCompute ----> reqid = " + ev.getReqid());
        System.out.println("terminateCompute ----> request deblocked");
        List<String> replist;
        replist = ev.getComputeIdList();
        Response resp;
        if ((replist == null) || (replist.isEmpty())) {
            resp = Response.ok(new String(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("terminateCompute ----> response ok");
        asyncResp.resume(resp);
    }
}
