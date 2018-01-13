package com.zenika.nckotlinserver.redis

import com.zenika.nckotlinserver.model.Entity
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
abstract class RedisValueRepository<V : Entity>(
        val prefix: String,
        template: RedisTemplate<String, V>
) : CrudRepository<V, String> {

    val opsForValue: ValueOperations<String, V> = template.opsForValue()

    fun keyForId(id: String?) = "$prefix:$id"

    override fun findById(id: String?): Optional<V> = Optional.ofNullable(opsForValue.get(keyForId(id)))

    override fun existsById(id: String?): Boolean = opsForValue.operations.hasKey(keyForId(id)) == true

    override fun findAllById(ids: MutableIterable<String>?): MutableIterable<V> = TODO("not implemented")
    override fun findAll(): MutableIterable<V> = TODO("not implemented")
    override fun deleteAll(entities: MutableIterable<V>?) = TODO("not implemented")
    override fun deleteAll() = TODO("not implemented")

    override fun <S : V?> save(entity: S): S {
        opsForValue.set(keyForId(entity?.id), entity)
        return entity
    }

    override fun <S : V?> saveAll(entities: MutableIterable<S>?): MutableIterable<S> = TODO("not implemented")
    override fun count(): Long = TODO("not implemented")
    override fun delete(entity: V?) = TODO("not implemented")
    override fun deleteById(id: String?) = TODO("not implemented")
}