package com.mtp.extinterface.nbi.swagger.api;

import com.mtp.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequest;
import com.mtp.extinterface.nbi.swagger.model.Filter;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualCompute;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualNetwork;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/reservations")
@Api(description = "the reservations API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class ReservationsApi {

    @POST
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualCompute.class, tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the reserved resource.", response = ReservedVirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "", response = Void.class) })
    public Response createComputeReservation(@Valid CreateComputeResourceReservationRequest body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualCompute.class, responseContainer = "List", tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the reserved resource. Cardinality is 0 if the query did not return any result.", response = ReservedVirtualCompute.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeReservation(@Valid Filter queryReservationFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/network-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualNetwork.class, responseContainer = "List", tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the reserved resource. Cardinality is 0 if the query did not return any result.", response = ReservedVirtualNetwork.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkReservation(@Valid Filter queryReservationFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier of the resource reservation(s) successfully terminated.", response = String.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateComputeReservation(@Valid String reservationId) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/network-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, tags={ "virtualisedResourceReservation" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier of the resource reservation(s) successfully terminated.", response = String.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateNetworkReservation(@Valid String reservationIds) {
        return Response.ok().entity("Not Implemented!").build();
    }
}
