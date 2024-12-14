package com.anycommunity.infra.redis.lock

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.redisson.api.RedissonClient
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.anycommunity.infra.redis.IntegrationTest

internal class DistributedLockTest(
    private val redisson: RedissonClient,
) : IntegrationTest, DescribeSpec(
    {
        redisson.isShutdown shouldBe false

        describe("DistributedLock 테스트") {
            it("100개 쓰레드에서 동시에 -1씩 수행 후 결과가 0인지 확인") {
                var count = 100
                val latch = CountDownLatch(100)
                Executors.newFixedThreadPool(100).apply {
                    repeat(100) {
                        execute {
                            distributedLock(key = "lockKey") {
                                count--
                            }
                            latch.countDown()
                        }
                    }
                }
                latch.await()
                count shouldBe 0
            }

            it("Lock 이 설정된 점유 시간 보다 길 경우 동시 접근에 대한 예외가 반환되어야 함") {
                var successCount = 0
                var errorCount = 0

                val lockKey = "testLock-${UUID.randomUUID()}"
                val latch = CountDownLatch(2)
                val executor = Executors.newFixedThreadPool(2)

                executor.execute {
                    distributedLock(key = lockKey) {
                        Thread.sleep(3000L)
                        successCount++
                    }
                    latch.countDown()
                }

                executor.execute {
                    try {
                        distributedLock(key = lockKey, waitTime = 1, timeUnit = TimeUnit.MILLISECONDS) {
                            successCount++
                        }
                    } catch (e: CannotAcquireLockException) {
                        errorCount++
                        latch.countDown()
                    }
                }

                latch.await()
                successCount shouldBe 1
                errorCount shouldBe 1
            }
        }
    },
)
