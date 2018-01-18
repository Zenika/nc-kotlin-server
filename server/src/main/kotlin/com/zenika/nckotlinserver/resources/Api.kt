package com.zenika.nckotlinserver.resources

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import javax.ws.rs.WebApplicationException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
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

@Provider
class CORSFilter : ContainerResponseFilter {
    override fun filter(requestContext: ContainerRequestContext?, responseContext: ContainerResponseContext?) {
        responseContext!!.headers!!.add("Access-Control-Allow-Origin", "*")

        if (requestContext!!.method != "OPTIONS") return

        requestContext.getHeaderString("Access-Control-Request-Headers").let { responseContext.headers.add("Access-Control-Allow-Headers", it) }
        requestContext.getHeaderString("Access-Control-Request-Methods").let { responseContext.headers.add("Access-Control-Allow-Methods", it) }
    }
}