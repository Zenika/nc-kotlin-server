package com.zenika.nckotlinserver.service.executor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity.entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE

@Service
class Executor(@Value("\${executor.url:http://localhost:3000}") val url: String) {

    fun getLanguages() = getExecutorTarget("language")
        .request()
        .get()
        .readEntity(object : GenericType<List<Language>>() {})
        .map { it.key }

    fun execute(language: String, _in: In) = getExecutorTarget("language/$language")
        .request()
        .post(entity(_in, APPLICATION_JSON_TYPE), Out::class.java)

    private fun getExecutorTarget(path: String): WebTarget {
        var target = getClient().target(url)
        target = target.path(path)
        return target
    }

    private fun getClient(): Client {
        val client = ClientBuilder.newClient()
        val provider = JacksonJaxbJsonProvider()
        provider.setMapper(jacksonObjectMapper())
        return client.register(provider)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Language(val key: String)

data class In(
    val code: String,
    val input: String
)

data class Out(
    val exitCode: Int,
    val out: String,
    val err: String
)