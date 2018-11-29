package com.mtp.extinterface.nbi.swagger.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;


@Path("/healthz")
@Api(description = "the healthz API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-10-03T12:52:58.036Z")
public class HealthzApi {

    @GET
    @ApiOperation(value = "Healthcheck", notes = "<br/>", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Healthcheck", response = Void.class) })
    public Response healthzGet() {
        return Response.ok().entity("magic!").build();
    }
}
