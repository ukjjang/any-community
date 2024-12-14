package com.anycommunity.infra.redis.lock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.lock.ExecuteIfAcquiredHandler.Companion.executeIfAcquiredForTransaction
import com.anycommunity.infra.redis.lock.ExecuteIfAcquiredHandler.Companion.log
import com.anycommunity.infra.redis.lock.ExecuteIfAcquiredHandler.Companion.redisTemplate
import com.anycommunity.util.logger.LazyLogger

@Component
class ExecuteIfAcquiredHandler(
    redisTemplate: RedisTemplate<String, String>,
    executeIfAcquiredForTransaction: ExecuteIfAcquiredForTransaction,
) {
    init {
        Companion.redisTemplate = redisTemplate
        Companion.executeIfAcquiredForTransaction = executeIfAcquiredForTransaction
    }

    companion object {
        internal lateinit var redisTemplate: RedisTemplate<String, String>
            private set

        internal lateinit var executeIfAcquiredForTransaction: ExecuteIfAcquiredForTransaction
            private set

        internal val log by LazyLogger()
    }
}

@Component
class ExecuteIfAcquiredForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(function: () -> T) = function()
}

fun executeIfAcquired(
    key: String,
    leaseTime: Long = 5L,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    transactional: Boolean = false,
    function: () -> Unit,
) {
    val lockKey = REDIS_EXECUTE_IF_ACQUIRED_KEY_PREFIX + key
    val lockValue = Thread.currentThread().name
    val acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, leaseTime, timeUnit) ?: false

    if (acquired) {
        try {
            if (transactional) {
                executeIfAcquiredForTransaction.proceed(function)
            } else {
                function()
            }
        } finally {
            if (redisTemplate.opsForValue().get(lockKey) == lockValue) {
                redisTemplate.delete(lockKey)
            } else {
                log.warn { "작업이 완료되기 전 락이 해제되었습니다. lockKey: $lockKey" }
            }
        }
    }
}

private const val REDIS_EXECUTE_IF_ACQUIRED_KEY_PREFIX = "executeIfAcquired:"
