package com.zenika.nckotlinserver.redis

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zenika.nckotlinserver.model.Entity
import com.zenika.nckotlinserver.model.Player
import com.zenika.nckotlinserver.model.Scenario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@PropertySource(value = "classpath:redis.properties", ignoreResourceNotFound = true)
class RedisConfiguration {

    @Bean
    fun connectionFactory(
            @Value("\${redis.hostName:localhost}") hostName: String,
            @Value("\${redis.port:6379}") port: Int
    ) = JedisConnectionFactory(RedisStandaloneConfiguration(hostName, port))

    @Bean
    fun scenarioRedisTemplate(@Autowired connectionFactory: JedisConnectionFactory)
            : RedisTemplate<String, Scenario> = redisTemplate(connectionFactory)

    @Bean
    fun playerRedisTemplate(@Autowired connectionFactory: JedisConnectionFactory)
            : RedisTemplate<String, Player> = redisTemplate(connectionFactory)

}

inline fun <reified E : Entity> redisTemplate(connectionFactory: JedisConnectionFactory): RedisTemplate<String, E> {
    val template = RedisTemplate<String, E>()
    template.connectionFactory = connectionFactory
    template.keySerializer = StringRedisSerializer()
    val valueSerializer = Jackson2JsonRedisSerializer(E::class.java)
    valueSerializer.setObjectMapper(jacksonObjectMapper().registerModule(JavaTimeModule()))
    template.valueSerializer = valueSerializer
    return template
}