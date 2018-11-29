package com.mtp.extinterface.nbi.swagger.api;

import com.mtp.extinterface.nbi.swagger.model.CreateComputeResourceQuotaRequest;
import com.mtp.extinterface.nbi.swagger.model.CreateNetworkResourceQuotaRequest;
import com.mtp.extinterface.nbi.swagger.model.Filter;
import com.mtp.extinterface.nbi.swagger.model.ResourceGroupIds;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeQuota;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetworkQuota;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/quotas")
@Api(description = "the quotas API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class QuotasApi {

    @POST
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeQuota.class, tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the quota resource.", response = VirtualComputeQuota.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response createComputeQuota(@Valid CreateComputeResourceQuotaRequest body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @POST
    @Path("/network-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkQuota.class, tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the quota resource.", response = VirtualNetworkQuota.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "", response = Void.class) })
    public Response createNetworkQuota(@Valid CreateNetworkResourceQuotaRequest body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeQuota.class, responseContainer = "List", tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the quota resource. The cardinality can be 0 if no matching quota exists.", response = VirtualComputeQuota.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeQuota(@Valid Filter queryQuotaFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/network-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkQuota.class, responseContainer = "List", tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the quota resource(s) matching the filter. The cardinality can be 0 if no matching quota exists.", response = VirtualNetworkQuota.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkQuota(@Valid Filter quotaQueryFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/compute-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ResourceGroupIds.class, tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.", response = ResourceGroupIds.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateComputeQuota(@Valid ResourceGroupIds resourceGroupId) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/network-resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ResourceGroupIds.class, tags={ "virtualisedResourceQuota" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.", response = ResourceGroupIds.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateNetworkQuota(@Valid ResourceGroupIds resourceGroupIds) {
        return Response.ok().entity("Not Implemented!").build();
    }
}
