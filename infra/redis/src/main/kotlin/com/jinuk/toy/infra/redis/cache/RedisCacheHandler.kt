package com.jinuk.toy.infra.redis.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.log
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.objectMapper
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.redisTemplate
import com.jinuk.toy.util.logger.LazyLogger

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
        val log by LazyLogger()
    }
}

inline fun <reified T> cached(
    key: String,
    expire: Duration = Duration.ofSeconds(300),
    noinline function: () -> T,
): T {
    try {
        val cachedValue = redisTemplate.opsForValue()[key]
        if (cachedValue != null) {
            return objectMapper.readValue(cachedValue, object : TypeReference<T>() {})
        }
    } catch (e: Exception) {
        log.error { "redis get fail: $key, ${e.stackTraceToString()}" }
    }

    val notCachedValue = function()
    try {
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(notCachedValue), expire)
    } catch (e: Exception) {
        log.error { "redis set fail: $key, ${e.stackTraceToString()}" }
    }
    return notCachedValue
}

fun cacheEvict(key: String) {
    try {
        redisTemplate.delete(key)
    } catch (e: Exception) {
        log.error { "redis delete fail: $key, error: ${e.stackTraceToString()}" }
    }
}
