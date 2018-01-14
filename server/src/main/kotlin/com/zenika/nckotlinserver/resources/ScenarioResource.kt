package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.model.Scenario
import com.zenika.nckotlinserver.repository.ScenarioRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("scenario")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class ScenarioResource {

    @Autowired
    lateinit var repository: ScenarioRepository

    @POST
    fun put(scenario: Scenario) = repository.set(scenario)

    @GET
    @Path("{language}")
    fun get(@PathParam("language") language: String) = repository.get(language)
}