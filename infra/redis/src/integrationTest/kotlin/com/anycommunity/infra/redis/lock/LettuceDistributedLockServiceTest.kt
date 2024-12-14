package com.anycommunity.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import com.anycommunity.infra.redis.IntegrationTest

internal class LettuceDistributedLockServiceTest(
    private val lettuceDistributedLockService: LettuceDistributedLockService,
) : IntegrationTest, DescribeSpec(
    {
        describe("Lettuce 의 DistributedLock 테스트") {
            it("100개 쓰레드에서 동시에 -1씩 수행 후 결과가 0인지 확인") {
                var count = 100
                val latch = CountDownLatch(100)
                Executors.newFixedThreadPool(100).apply {
                    repeat(100) {
                        execute {
                            lettuceDistributedLockService.distributedLock(key = "lockKey") {
                                count--
                            }
                            latch.countDown()
                        }
                    }
                }
                latch.await()
                count shouldBe 0
            }
        }
    },
)
