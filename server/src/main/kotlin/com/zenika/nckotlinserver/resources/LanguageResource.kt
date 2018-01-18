package com.zenika.nckotlinserver.resources

import com.zenika.nckotlinserver.service.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("language")
@Produces(APPLICATION_JSON)
class LanguageResource {

    @Autowired
    lateinit var service: LanguageService

    @GET
    fun get() = service.list()
}