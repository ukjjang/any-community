package com.anycommunity.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import com.anycommunity.infra.redis.IntegrationTest

internal class ExecuteIfAcquiredTest(
    private val redisTemplate: RedisTemplate<String, String>,
) : IntegrationTest, DescribeSpec(
    {
        describe("ExecuteIfAcquired 테스트") {
            val key = UUID.randomUUID().toString()
            redisTemplate.delete(key)

            it("10개 쓰레드에서 동시에 수행 후 최초 요청만 처리되었는지 확인") {
                var count = 0
                val latch = CountDownLatch(10)
                Executors.newFixedThreadPool(10).apply {
                    repeat(10) {
                        execute {
                            try {
                                executeIfAcquired(key = key) {
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
