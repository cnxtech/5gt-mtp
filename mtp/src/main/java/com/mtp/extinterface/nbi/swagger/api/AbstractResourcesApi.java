package com.mtp.extinterface.nbi.swagger.api;



import com.google.common.eventbus.Subscribe;
import com.mtp.SingletonEventBus;
import com.mtp.events.abstraction.Advertisement.AdvertiseE2EAbstractionReply;
import com.mtp.events.abstraction.Advertisement.AdvertiseE2EAbstractionRequest;
import com.mtp.extinterface.nbi.swagger.model.InlineResponse2001;
import javax.ws.rs.*;

import io.swagger.annotations.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;




@Path("/abstract-resources")
@Api(description = "the abstract-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-23T15:51:40.129Z")
public class AbstractResourcesApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public AbstractResourcesApi() {
        //reqid = 0;

    }

    @GET
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", notes = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", response = InlineResponse2001.class, tags={ "abstractResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2001.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void collectMtpCloudNetworkResourceInformation(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("collectMtpCloudNetworkResourceInformation ----> retrieve abstract resources request suspended");
        System.out.println("collectMtpCloudNetworkResourceInformation ----> Calling post");
        reqid++;
        System.out.println("collectMtpCloudNetworkResourceInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        //   E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid,10, params);
        AdvertiseE2EAbstractionRequest request = new AdvertiseE2EAbstractionRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_AdvertiseE2EAbstractionReply(AdvertiseE2EAbstractionReply ev) throws InterruptedException {
        System.out.println("AdvertiseE2EAbstractionReply ----> handle advertise abstracted resources");
        //send response
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("AdvertiseE2EAbstractionReply ----> reqid = " + ev.getReqid());
        System.out.println("allocateNAdvertiseE2EAbstractionReplyetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    
}
