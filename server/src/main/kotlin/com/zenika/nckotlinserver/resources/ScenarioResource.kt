package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.model.InternalState
import com.zenika.nckotlinserver.model.Scenario
import com.zenika.nckotlinserver.repository.ScenarioRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("scenario/{language}")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class ScenarioResource {

    @Autowired
    lateinit var repository: ScenarioRepository

    @PUT
    fun put(@PathParam("language") language: String, scenario: Scenario) {
        if (scenario.language != language) {
            throw BadRequestException()
        }
        repository.save(scenario)
    }

    @GET
    fun get(@PathParam("language") language: String) = repository.findById(language)
}