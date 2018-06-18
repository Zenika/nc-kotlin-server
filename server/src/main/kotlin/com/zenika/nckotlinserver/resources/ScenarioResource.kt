package com.zenika.nckotlinserver.resources

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zenika.nckotlinserver.model.Scenario
import com.zenika.nckotlinserver.repository.ScenarioRepository
import com.zenika.nckotlinserver.service.executor.Executor
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.MediaType.TEXT_PLAIN

@Path("scenario")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class ScenarioResource {

    @Autowired
    lateinit var repository: ScenarioRepository

    @Autowired
    lateinit var executor: Executor

    @POST
    fun put(scenario: Scenario) = repository.set(scenario)

    @GET
    @Path("{language}")
    fun get(@PathParam("language") language: String) = repository.get(language) ?:
    throw NotFoundException("No scenario for language $language")

    @GET
    @Path("hydrate")
    @Produces(TEXT_PLAIN)
    fun hydrate() = executor.getLanguages()
        .map { "/scenarios/$it.json" }
        .mapNotNull(ScenarioResource::class.java::getResource)
        .map { jacksonObjectMapper().readValue(it, Scenario::class.java) }
        .map(repository::set)
        .map { "Loaded scenario for ${it.language}" }
        .joinToString("\n")
}