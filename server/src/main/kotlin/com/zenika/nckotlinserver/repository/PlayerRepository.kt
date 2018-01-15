package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.Player
import com.zenika.nckotlinserver.redis.RedisValueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PlayerRepository(@Autowired val template: RedisTemplate<String, Player>)
    : RedisValueRepository<Player>("player", template)

