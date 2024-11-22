package com.anycommunity.infra.redis.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import com.anycommunity.infra.redis.cache.RedisCacheHandler.Companion.log
import com.anycommunity.infra.redis.cache.RedisCacheHandler.Companion.objectMapper
import com.anycommunity.infra.redis.cache.RedisCacheHandler.Companion.redisTemplate
import com.anycommunity.util.logger.LazyLogger

@Component
class RedisCacheHandler(
    redisTemplate: RedisTemplate<String, String>,
    objectMapper: ObjectMapper,
) {
    init {
        Companion.redisTemplate = redisTemplate
        Companion.objectMapper = objectMapper
    }

    companion object {
        lateinit var redisTemplate: RedisTemplate<String, String>
            private set

        lateinit var objectMapper: ObjectMapper
            private set

        val log by LazyLogger()
    }
}

inline fun <reified T> cached(key: String, expire: Duration = Duration.ofMinutes(5), noinline function: () -> T): T {
    val redisCacheKeyPrefix = "cache:"
    val cacheKey = redisCacheKeyPrefix + key
    try {
        val cachedValue = redisTemplate.opsForValue()[cacheKey]
        if (cachedValue != null) {
            return objectMapper.readValue(cachedValue, object : TypeReference<T>() {})
        }
    } catch (e: Exception) {
        log.error { "redis get fail: $cacheKey, ${e.stackTraceToString()}" }
    }

    val notCachedValue = function()
    try {
        redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(notCachedValue), expire)
    } catch (e: Exception) {
        log.error { "redis set fail: $cacheKey, ${e.stackTraceToString()}" }
    }
    return notCachedValue
}
