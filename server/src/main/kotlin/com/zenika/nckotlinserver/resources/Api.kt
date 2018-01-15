package com.zenika.nckotlinserver.resources

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response.status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Configuration
class JerseyConfig : ResourceConfig() {
    init {
        packages("com.zenika.nckotlinserver.resources")
    }
}

@Provider
class WebApplicationExceptionMapper : ExceptionMapper<WebApplicationException> {
    override fun toResponse(exception: WebApplicationException?) = status(exception!!.response.status).entity(ErrorEntity(exception.message)).build()
}

data class ErrorEntity(val error: String?)