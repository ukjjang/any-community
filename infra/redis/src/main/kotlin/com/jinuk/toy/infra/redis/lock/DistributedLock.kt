package com.jinuk.toy.infra.redis.lock

import java.util.concurrent.TimeUnit

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DistributedLock(
    val key: String,
    val waitTime: Long = 10L,
    val leaseTime: Long = 5L,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    val transactional: Boolean = false,
)
