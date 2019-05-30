package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.nbi.swagger.model.AppInstanceInfo;
import com.ericsson.dummyplugin.nbi.swagger.model.CreateAppInstanceIdentifierRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.CreateAppInstanceIdentifierResponse;
import com.ericsson.dummyplugin.nbi.swagger.model.DeleteAppInstanceIdentifierRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InstantiateAppRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InstantiateAppResponse;
import com.ericsson.dummyplugin.nbi.swagger.model.TerminateAppInsRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.TerminateAppInsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/instance")
@Api(description = "the instance API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class InstanceApi {

    @POST
    @Path("/createId")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create an instance id.", notes = "Create an instance id, which will be used in future lifecycle operations. The instance is not yet instantiated. Querying for the instance with the given id will report the instance as NOT_INSTANTIATED.", response = CreateAppInstanceIdentifierResponse.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Instance ID created successfully.", response = CreateAppInstanceIdentifierResponse.class)
    })
    public Response instanceCreateIdPost(@Valid CreateAppInstanceIdentifierRequest createAppInstanceIdentifierRequest) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/deleteId")
    @Consumes({ "application/json" })
    @ApiOperation(value = "Delete an instance id.", notes = "Delete an instance id, if the instance is in the NOT_INSTANTIATED state. Again, we are not using DELETE since this method has a (minimal) body and some HTTP clients do not like DELETEs with bodies.", response = Void.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Instance ID deleted successfully.", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request.", response = Void.class),
        @ApiResponse(code = 404, message = "Instance ID not found.", response = Void.class)
    })
    public Response instanceDeleteIdPost(@Valid DeleteAppInstanceIdentifierRequest deleteAppInstanceIdentifierRequest) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/instantiate")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Instantiate an application instance.", notes = "Create an application instance. The body of the request includes the instance ID which should have been created by a prior call to createId. If  virtual compute/storage descriptors are included in the body of the request, they will override the defaults that are included in the appD.", response = InstantiateAppResponse.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Application instantiation successfully requested.", response = InstantiateAppResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 404, message = "Instance ID not found", response = Void.class)
    })
    public Response instanceInstantiatePost(@Valid InstantiateAppRequest instantiateAppRequest) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/query")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of instances.", notes = "Retrieve a list of instances.", response = AppInstanceInfo.class, responseContainer = "List", tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of application instance information.", response = AppInstanceInfo.class, responseContainer = "List")
    })
    public Response instanceQueryGet() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/query/{instanceId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve information about a specific instance.", notes = "Retrieve information about a specific instance.", response = AppInstanceInfo.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Application instance information.", response = AppInstanceInfo.class),
        @ApiResponse(code = 404, message = "Instance not found.", response = Void.class)
    })
    public Response instanceQueryInstanceIdGet(@PathParam("instanceId") @ApiParam("Application instance id.") String instanceId) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/terminate")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Stop application instance(s).", notes = "This action terminates one or more application instance. Note that we are using HTTP POST since the request includes a body and there could be HTTP clients facing issues with a DELETE with a body.", response = TerminateAppInsResponse.class, tags={ "MECResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Instance(s) successfully terminated.", response = TerminateAppInsResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 404, message = "Instance ID not found", response = Void.class)
    })
    public Response instanceTerminatePost(@Valid TerminateAppInsRequest terminateAppInsRequest) {
        return Response.ok().entity("magic!").build();
    }
}
