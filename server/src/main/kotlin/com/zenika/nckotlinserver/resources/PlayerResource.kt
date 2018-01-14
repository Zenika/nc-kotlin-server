package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.model.PlayerCreation
import com.zenika.nckotlinserver.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("player")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class PlayerResource {

    @Autowired
    lateinit var service: PlayerService

    @POST
    fun post(playerCreation: PlayerCreation) = service.createPlayer(playerCreation)

    @Path("{playerId}")
    @GET
    fun get(@PathParam("playerId") playerId: String) = service.getPlayer(playerId)
}