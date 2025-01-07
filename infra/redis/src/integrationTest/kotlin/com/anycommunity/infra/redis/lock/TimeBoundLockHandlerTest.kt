package com.anycommunity.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.IntegrationTest

internal class TimeBoundLockHandlerTest(
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTest, DescribeSpec(
    {
        val key = UUID.randomUUID().toString()
        redisTemplate.delete(key)

        describe("TimeBoundLock 테스트") {
            it("10개 쓰레드에서 동시에 수행 후 최초 요청만 처리되었는지 확인") {
                var count = 0
                val latch = CountDownLatch(10)
                Executors.newFixedThreadPool(10).apply {
                    repeat(10) {
                        execute {
                            try {
                                timeBoundLock(key = key, leaseTime = 1, timeUnit = TimeUnit.SECONDS) {
                                    count++
                                }
                            } finally {
                                latch.countDown()
                            }
                        }
                    }
                }
                latch.await()
                count shouldBe 1
            }
        }
    },
)
