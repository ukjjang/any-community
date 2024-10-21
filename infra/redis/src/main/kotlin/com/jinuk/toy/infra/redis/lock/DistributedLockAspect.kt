package com.jinuk.toy.infra.redis.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Aspect
@Component
internal class DistributedLockAspect(
    private val redisson: RedissonClient,
    private val aspectForTransaction: AspectForTransaction,
) {
    @Around("@annotation(com.jinuk.toy.infra.redis.lock.DistributedLock)")
    fun aroundDistributeLock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as? MethodSignature ?: throw IllegalArgumentException("Invalid signature")
        val method = signature.method

        val distributedLock = method.getAnnotation(DistributedLock::class.java)
        val dynamicValue = CustomSpELParser.dynamicValue(signature.parameterNames, joinPoint.args, distributedLock.key)
        val key = REDISSON_KEY_PREFIX + dynamicValue
        val redisLock = redisson.getLock(key)
        if (!redisLock.tryLock(distributedLock.waitTime, distributedLock.leaseTime, distributedLock.timeUnit)) {
            throw CannotAcquireLockException("Could not acquire lock. key: $key")
        }

        return try {
            if (distributedLock.transactional) {
                aspectForTransaction.proceed(joinPoint)
            } else {
                joinPoint.proceed()
            }
        } finally {
            if (redisLock.isLocked && redisLock.isHeldByCurrentThread) {
                redisLock.unlock()
            }
        }
    }

    companion object {
        private const val REDISSON_KEY_PREFIX = "RLOCK:"
    }
}

@Component
internal class AspectForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? = joinPoint.proceed()
}

class CannotAcquireLockException(message: String) : RuntimeException(message)
