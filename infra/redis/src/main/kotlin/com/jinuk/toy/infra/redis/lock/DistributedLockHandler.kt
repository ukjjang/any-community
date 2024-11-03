package com.jinuk.toy.infra.redis.lock

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.jinuk.toy.infra.redis.lock.DistributedLockHandler.Companion.distributedLockForTransaction
import com.jinuk.toy.infra.redis.lock.DistributedLockHandler.Companion.redisson

@Component
class DistributedLockHandler(
    _redisson: RedissonClient,
    _distributedLockForTransaction: DistributedLockForTransaction,
) {
    init {
        redisson = _redisson
        distributedLockForTransaction = _distributedLockForTransaction
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
    val redisLock = redisson.getLock(key)
    if (!redisLock.tryLock(waitTime, leaseTime, timeUnit)) {
        throw CannotAcquireLockException("Could not acquire lock. key: $key")
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
