package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.service.StateService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("player/{playerId}/state")
@Produces(APPLICATION_JSON)
class StateResource {
    @Autowired
    lateinit var service: StateService

    @GET
    fun get(@PathParam("playerId") playerId: String) = service.getState(playerId)
}