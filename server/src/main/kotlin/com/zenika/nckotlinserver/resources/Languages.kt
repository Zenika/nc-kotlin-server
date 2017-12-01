package com.zenika.nckotlinserver.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("languages")
@Produces(APPLICATION_JSON)
class Languages {

    @GET
    fun get() = listOf<String>("js", "kotlin")
}