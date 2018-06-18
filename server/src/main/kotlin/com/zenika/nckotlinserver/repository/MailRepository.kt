package com.zenika.nckotlinserver.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

val key = "mail"

@Repository
class MailRepository(@Autowired val mailTemplate: RedisTemplate<String, String>) {
    fun add(mail: String) = mailTemplate.opsForSet().add(key, mail)
    fun exists(mail: String) = mailTemplate.opsForSet().isMember(key, mail)!!
}