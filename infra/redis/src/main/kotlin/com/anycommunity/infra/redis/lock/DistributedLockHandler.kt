package com.anycommunity.infra.redis.lock

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.lock.DistributedLockHandler.Companion.distributedLockForTransaction
import com.anycommunity.infra.redis.lock.DistributedLockHandler.Companion.redisson

@Component
class DistributedLockHandler(
    redisson: RedissonClient,
    distributedLockForTransaction: DistributedLockForTransaction,
) {
    init {
        Companion.redisson = redisson
        Companion.distributedLockForTransaction = distributedLockForTransaction
    }

    companion object {
        lateinit var redisson: RedissonClient
            private set

        lateinit var distributedLockForTransaction: DistributedLockForTransaction
            private set
    }
}

@Component
class DistributedLockForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(function: () -> T) = function()
}

fun <T> distributedLock(
    key: String,
    waitTime: Long = 10L,
    leaseTime: Long = 5L,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    transactional: Boolean = false,
    function: () -> T,
): T {
    val lockKey = REDIS_LOCK_KEY_PREFIX + key
    val redisLock = redisson.getLock(lockKey)
    if (!redisLock.tryLock(waitTime, leaseTime, timeUnit)) {
        throw CannotAcquireLockException("Could not acquire lock. lockKey: $lockKey")
    }

    return try {
        if (transactional) {
            distributedLockForTransaction.proceed(function)
        } else {
            function()
        }
    } finally {
        if (redisLock.isLocked && redisLock.isHeldByCurrentThread) {
            redisLock.unlock()
        }
    }
}

class CannotAcquireLockException(message: String) : RuntimeException(message)

private const val REDIS_LOCK_KEY_PREFIX = "lock:"
