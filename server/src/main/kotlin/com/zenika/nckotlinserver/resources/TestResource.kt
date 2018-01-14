package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.model.Code
import com.zenika.nckotlinserver.service.TestService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("player/{playerId}/test")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class TestResource {
    @Autowired
    lateinit var service: TestService

    @POST
    fun post(@PathParam("playerId") playerId: String, code: Code) = service.executeTest(playerId, code)
}