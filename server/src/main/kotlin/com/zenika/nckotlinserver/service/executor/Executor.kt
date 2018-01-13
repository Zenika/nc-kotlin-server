package com.zenika.nckotlinserver.service.executor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.GenericType

@Service
class Executor(@Value("\${executor.url:http://localhost:3000}") val url: String) {

    fun getLanguages() = getExecutorTarget("language")
            .request()
            .get()
            .readEntity(object : GenericType<List<ExecutorLanguage>>() {})
            .map { it.key }

    private fun getExecutorTarget(path: String): WebTarget {
        var target = getClient().target(url)
        target = target.path(path)
        return target
    }

    private fun getClient(): Client {
        var client = ClientBuilder.newClient()
        val provider = JacksonJaxbJsonProvider()
        provider.setMapper(jacksonObjectMapper())
        return client.register(provider)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExecutorLanguage(val key: String)
