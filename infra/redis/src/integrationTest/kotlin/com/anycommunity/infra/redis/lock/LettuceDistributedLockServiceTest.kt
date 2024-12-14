package com.anycommunity.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import com.anycommunity.infra.redis.IntegrationTest

internal class LettuceDistributedLockServiceTest(
    private val lettuceDistributedLockService: LettuceDistributedLockService,
) : IntegrationTest, DescribeSpec(
    {
        describe("Lettuce 의 DistributedLock 테스트") {
            it("10개 쓰레드에서 동시에 -1씩 수행 후 결과가 0인지 확인") {
                val key = UUID.randomUUID().toString()
                var count = 10
                val latch = CountDownLatch(10)
                Executors.newFixedThreadPool(10).apply {
                    repeat(10) {
                        execute {
                            try {
                                lettuceDistributedLockService.distributedLock(key = key) {
                                    count--
                                }
                            } finally {
                                latch.countDown()
                            }
                        }
                    }
                }
                latch.await()
                count shouldBe 0
            }
        }
    },
)
