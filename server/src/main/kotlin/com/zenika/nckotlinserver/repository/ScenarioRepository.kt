package com.zenika.nckotlinserver.repository

import com.zenika.nckotlinserver.model.Scenario
import com.zenika.nckotlinserver.redis.RedisValueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ScenarioRepository(@Autowired val scenarioTemplate: RedisTemplate<String, Scenario?>)
    : RedisValueRepository<Scenario>("scenario", scenarioTemplate)
