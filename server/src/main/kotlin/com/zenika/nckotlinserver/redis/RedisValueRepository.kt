package com.zenika.nckotlinserver.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
abstract class RedisValueRepository<V>(
        val prefix: String,
        val template: RedisTemplate<String, V?>
) : CrudRepository<V, String> {

    fun keyForId(id: String?) = "$prefix:$id"

    override fun findById(id: String?): Optional<V> = Optional.ofNullable(template.opsForValue().get(keyForId(id)))

    override fun existsById(id: String?): Boolean = template.opsForValue().operations.hasKey(keyForId(id))

    override fun findAllById(ids: MutableIterable<String>?): MutableIterable<V> = TODO("not implemented")
    override fun findAll(): MutableIterable<V> = TODO("not implemented")
    override fun deleteAll(entities: MutableIterable<V>?) = TODO("not implemented")
    override fun deleteAll() = TODO("not implemented")
    override fun <S : V?> save(entity: S): S = TODO("not implemented")
    override fun <S : V?> saveAll(entities: MutableIterable<S>?): MutableIterable<S> = TODO("not implemented")
    override fun count(): Long = TODO("not implemented")
    override fun delete(entity: V?) = TODO("not implemented")
    override fun deleteById(id: String?) = TODO("not implemented")
}