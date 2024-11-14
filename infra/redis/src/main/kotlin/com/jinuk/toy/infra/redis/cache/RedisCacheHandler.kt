package com.jinuk.toy.infra.redis.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.jinuk.toy.common.util.logger.LazyLogger
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.cacheForTransaction
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.log
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.objectMapper
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.redisTemplate

@Component
class RedisCacheHandler(
    redisTemplate: RedisTemplate<String, String>,
    objectMapper: ObjectMapper,
    cacheForTransaction: CacheForTransaction,
) {
    init {
        Companion.redisTemplate = redisTemplate
        Companion.objectMapper = objectMapper
        Companion.cacheForTransaction = cacheForTransaction
    }

    companion object {
        lateinit var redisTemplate: RedisTemplate<String, String>
            private set

        lateinit var objectMapper: ObjectMapper
            private set

        lateinit var cacheForTransaction: CacheForTransaction
            private set
        val log by LazyLogger()
    }
}

@Component
class CacheForTransaction {
    @Transactional(readOnly = true)
    fun <T> proceed(function: () -> T) = function()
}

fun <T> cached(
    key: String,
    expire: Duration = Duration.ofSeconds(300),
    transactional: Boolean = false,
    function: () -> T,
): T {
    val cacheKey = REDIS_CACHE_KEY_PREFIX + key
    try {
        val cachedValue = redisTemplate.opsForValue()[cacheKey]
        if (cachedValue != null) {
            return objectMapper.readValue(cachedValue, object : TypeReference<T>() {})
        }
    } catch (e: Exception) {
        log.error { "redis get fail: $cacheKey, ${e.stackTraceToString()}" }
    }

    val notCachedValue =
        if (transactional) {
            cacheForTransaction.proceed(function)
        } else {
            function()
        }

    try {
        redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(notCachedValue), expire)
    } catch (e: Exception) {
        log.error { "redis set fail: $cacheKey, ${e.stackTraceToString()}" }
    }
    return notCachedValue
}

fun cacheEvict(key: String) {
    val cacheKey = REDIS_CACHE_KEY_PREFIX + key
    try {
        redisTemplate.delete(cacheKey)
    } catch (e: Exception) {
        log.error { "redis delete fail: $cacheKey, error: ${e.stackTraceToString()}" }
    }
}

private const val REDIS_CACHE_KEY_PREFIX = "cache:"
