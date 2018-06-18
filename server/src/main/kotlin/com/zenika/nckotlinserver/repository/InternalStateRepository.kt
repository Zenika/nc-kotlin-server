package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.InternalState
import com.zenika.nckotlinserver.redis.RedisValueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class InternalStateRepository(@Autowired val template: RedisTemplate<String, InternalState>)
    : RedisValueRepository<InternalState>({ "player:$it:state" }, template)