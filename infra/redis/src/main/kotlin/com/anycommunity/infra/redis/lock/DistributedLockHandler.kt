package com.anycommunity.infra.redis.lock

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.lock.DistributedLockHandler.Companion.distributedLockForTransaction
import com.anycommunity.infra.redis.lock.DistributedLockHandler.Companion.log
import com.anycommunity.infra.redis.lock.DistributedLockHandler.Companion.redisson
import com.anycommunity.util.logger.LazyLogger

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
        internal lateinit var redisson: RedissonClient
            private set

        internal lateinit var distributedLockForTransaction: DistributedLockForTransaction
            private set

        internal val log by LazyLogger()
    }
}

@Component
class DistributedLockForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(function: () -> T) = function()
}

/**
 * 분산 환경에서 락을 획득하여 주어진 로직을 실행한다.
 * 락을 획득하지 못하면 CannotAcquireLockException 에러를 던진다.
 * 로직 실행이 끝난 후에는 락을 반환하며, 설정된 시간이 지나도 자동으로 락이 해제된다.
 */
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
    val acquired = redisLock.tryLock(waitTime, leaseTime, timeUnit)
    if (!acquired) {
        throw CannotAcquireLockException("락 획득에 실패했습니다. lockKey: $lockKey")
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
        } else {
            log.warn { "작업이 완료되기 전 락이 해제되었습니다. lockKey: $lockKey" }
        }
    }
}

class CannotAcquireLockException(message: String) : RuntimeException(message)

private const val REDIS_LOCK_KEY_PREFIX = "lock:"
