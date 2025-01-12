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

/**
 * 락을 획득한 경우에만 주어진 로직을 실행한다.
 * 락 획득에 실패하면 로직은 실행하지 않고 무시하고 넘어간다.
 * 로직 실행이 끝난 후에는 락을 반환하며, 설정된 시간이 지나도 자동으로 락이 해제된다.
 */
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
            if (redisTemplate.opsForValue()[lockKey] == lockValue) {
                redisTemplate.delete(lockKey)
            } else {
                log.warn { "작업이 완료되기 전 락이 해제되었습니다. lockKey: $lockKey" }
            }
        }
    }
}

private const val REDIS_EXECUTE_IF_ACQUIRED_KEY_PREFIX = "executeIfAcquired:"
