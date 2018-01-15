package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.service.ResultService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("result")
@Produces(APPLICATION_JSON)
class ResultResource {
    @Autowired
    lateinit var service: ResultService

    @GET
    fun get() = service.list()
}