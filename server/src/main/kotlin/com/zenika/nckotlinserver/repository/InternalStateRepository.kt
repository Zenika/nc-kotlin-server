package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.InternalState
import com.zenika.nckotlinserver.redis.RedisValueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class InternalStateRepository(@Autowired val internalStateTemplate: RedisTemplate<String, InternalState>)
    : RedisValueRepository<InternalState>({ "player:$it:state" }, internalStateTemplate)