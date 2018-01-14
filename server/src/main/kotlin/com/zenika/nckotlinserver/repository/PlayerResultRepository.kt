package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.PlayerResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PlayerResultRepository(@Autowired val template: RedisTemplate<String, PlayerResult>) {
    val opsForList = template.opsForList()

    fun add(playerResult: PlayerResult) {
        opsForList.rightPush("playerResult", playerResult)
    }

    fun getAll() = opsForList.range("playerResult", 0, -1)
}