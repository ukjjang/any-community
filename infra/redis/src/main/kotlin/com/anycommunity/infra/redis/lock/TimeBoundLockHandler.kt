package com.anycommunity.infra.redis.lock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.lock.TimeBoundLockHandler.Companion.redisTemplate
import com.anycommunity.infra.redis.lock.TimeBoundLockHandler.Companion.timeBoundLockForTransaction

@Component
class TimeBoundLockHandler(
    redisTemplate: RedisTemplate<String, String>,
    timeBoundLockForTransaction: TimeBoundLockForTransaction,
) {
    init {
        Companion.redisTemplate = redisTemplate
        Companion.timeBoundLockForTransaction = timeBoundLockForTransaction
    }

    companion object {
        internal lateinit var redisTemplate: RedisTemplate<String, String>
            private set

        internal lateinit var timeBoundLockForTransaction: TimeBoundLockForTransaction
            private set
    }
}

@Component
class TimeBoundLockForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(function: () -> T) = function()
}

/**
 * 설정된 시간 동안 락을 점유하며, 시간이 지나야만 락이 해제된다.
 * 락을 획득하지 못하면 로직은 실행하지 않고 무시하고 넘어간다.
 * 로직 실행이 끝난 후에도 락은 반환되지 않고 설정된 시간 동안 유지된다.
 */
fun timeBoundLock(
    key: String,
    leaseTime: Long = 5L,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    transactional: Boolean = false,
    function: () -> Unit,
) {
    val lockKey = REDIS_TIME_BOUND_LOCK_KEY_PREFIX + key
    val lockValue = Thread.currentThread().name
    val acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, leaseTime, timeUnit) ?: false

    if (acquired) {
        if (transactional) {
            timeBoundLockForTransaction.proceed(function)
        } else {
            function.invoke()
        }
    }
}

private const val REDIS_TIME_BOUND_LOCK_KEY_PREFIX = "timeBoundLock:"
