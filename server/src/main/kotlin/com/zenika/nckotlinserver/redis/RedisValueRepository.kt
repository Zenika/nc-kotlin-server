package com.zenika.nckotlinserver.redis

import com.zenika.nckotlinserver.model.Entity
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import org.springframework.data.repository.Repository as DataRepository

@Repository
abstract class RedisValueRepository<E : Entity>(
        val keyForId: (String) -> String,
        template: RedisTemplate<String, E>
) : DataRepository<E, String> {

    constructor(
            prefix: String,
            template: RedisTemplate<String, E>
    ) : this(
            { "$prefix:$it" },
            template
    )

    val opsForValue: ValueOperations<String, E> = template.opsForValue()

    fun get(id: String): E? = opsForValue.get(keyForId(id))

    fun exists(id: String): Boolean = opsForValue.operations.hasKey(keyForId(id))!!

    fun set(entity: E): E {
        opsForValue.set(keyForId(entity.id()), entity)
        return entity
    }
}