package com.zenika.nckotlinserver.executor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.GenericType

fun getExecutorTarget(path: String): WebTarget {
    var client = ClientBuilder.newClient()
    val provider = JacksonJaxbJsonProvider()
    provider.setMapper(jacksonObjectMapper())
    client = client.register(provider)
    var target = client.target("http://localhost:3000")
    target = target.path(path)
    return target
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExecutorLanguage(val key: String)

fun getLanguages(): List<String> = getExecutorTarget("language").request().get().readEntity(object: GenericType<List<ExecutorLanguage>>(){}).map { it.key }