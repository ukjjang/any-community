package com.jinuk.toy.infra.redis.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.log
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.objectMapper
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.redisTemplate
import com.jinuk.toy.util.logger.LazyLogger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisCacheHandler(
    _redisTemplate: RedisTemplate<String, String>,
    _objectMapper: ObjectMapper,
) {

    init {
        redisTemplate = _redisTemplate
        objectMapper = _objectMapper
    }

    companion object {
        lateinit var redisTemplate: RedisTemplate<String, String>
            private set

        lateinit var objectMapper: ObjectMapper
            private set

        internal val log by LazyLogger()
    }
}

fun <T> cached(
    key: String,
    expire: Duration = Duration.ofSeconds(300),
    returnType: TypeReference<T>,
    function: () -> T,
): T {
    try {
        val cachedValue = redisTemplate.opsForValue()[key]
        if (cachedValue != null) {
            return objectMapper.readValue(cachedValue, returnType)
        }
    } catch (e: Exception) {
        log.error { "redis get fail: $key, ${e.stackTraceToString()}" }
    }

    val notCachedValued = function()
    try {
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(notCachedValued), expire)
    } catch (e: Exception) {
        log.error { "redis set fail: $key, ${e.stackTraceToString()}" }
    }
    return notCachedValued
}

fun cacheEvict(key: String) {
    try {
        redisTemplate.delete(key)
    } catch (e: Exception) {
        log.error { "redis delete fail: $key, error: ${e.stackTraceToString()}" }
    }
}
