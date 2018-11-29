package com.mtp.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.events.abstraction.Advertisement.NetworkResourceInformationReply;
import com.mtp.events.abstraction.Advertisement.NetworkResourceInformationRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.ArrayList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/information")
@Api(description = "the information API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class InformationApi {
    private static Map <String,AsyncResponse> suspended = new ConcurrentHashMap(); 
    private static long reqid = 0;
    public InformationApi() {
        
    }
    @GET
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkResourceInformation.class, responseContainer = "List", tags={ "virtualisedNetworkResourcesInformation" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Virtualised network resource information in the VIM that satisfies the query condition.", response = VirtualNetworkResourceInformation.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void queryNetworkInformation(@Suspended final AsyncResponse ar, @QueryParam("filter") String informationQueryFilter) throws InterruptedException {
        System.out.println("queryNetworkInformation ----> network-information request suspended");
        System.out.println("queryNetworkInformation ----> Calling post");
        reqid++;
        System.out.println("queryNetworkInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        SingletonEventBus.getBus().post(new NetworkResourceInformationRequest(reqid, informationQueryFilter));
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    
    //Subscribe Event
    @Subscribe
    public void handle_NetworkResourceInformationReply(NetworkResourceInformationReply ev) throws InterruptedException {
        System.out.println("queryNetworkInformation ----> handle testrest event");
        //AsyncResponse asyncResp = suspended.take();
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNetworkInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryNetworkInformation ----> request deblocked");
        ArrayList<VirtualNetworkResourceInformation> reslist;
        reslist = ev.getnetresinfolist();
        Response resp;
        if (reslist.isEmpty()) {
            resp = Response.ok(new VirtualNetworkResourceInformation(), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(reslist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("queryNetworkInformation ----> response ok");
        asyncResp.resume(resp);
    }
}
