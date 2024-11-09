package com.jinuk.toy.infra.redis.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.cacheForTransaction
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.log
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.objectMapper
import com.jinuk.toy.infra.redis.cache.RedisCacheHandler.Companion.redisTemplate
import com.jinuk.toy.util.logger.LazyLogger

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
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    fun <T> proceed(function: () -> T) = function()
}

inline fun <reified T> cached(
    key: String,
    expire: Duration = Duration.ofSeconds(300),
    transactional: Boolean = false,
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

    val notCachedValue =
        if (transactional) {
            cacheForTransaction.proceed(function)
        } else {
            function()
        }

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
