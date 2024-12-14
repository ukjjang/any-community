package com.anycommunity.infra.redis.lock

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import com.anycommunity.util.logger.LazyLogger

@Service
class LettuceDistributedLockService(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    companion object {
        private const val REDIS_LOCK_KEY_PREFIX = "lock:"
        private val log by LazyLogger()
    }

    fun <T> distributedLock(key: String, waitTime: Long = 10000L, leaseTime: Long = 5000L, function: () -> T): T {
        val lockKey = REDIS_LOCK_KEY_PREFIX + key
        val lockValue = Thread.currentThread().name
        val endTime = System.currentTimeMillis() + waitTime
        while (System.currentTimeMillis() < endTime) {
            val acquired =
                redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, leaseTime, TimeUnit.MILLISECONDS) ?: false
            if (acquired) {
                try {
                    return function()
                } finally {
                    val currentValue = redisTemplate.opsForValue()[lockKey]
                    if (currentValue == lockValue) {
                        redisTemplate.delete(lockKey)
                    } else {
                        log.warn { "작업이 완료되기 전 락이 해제되었습니다. lockKey: $lockKey" }
                    }
                }
            }
            Thread.sleep(50)
        }
        throw CannotAcquireLockException("락 획득에 실패했습니다. lockKey: $lockKey")
    }
}
