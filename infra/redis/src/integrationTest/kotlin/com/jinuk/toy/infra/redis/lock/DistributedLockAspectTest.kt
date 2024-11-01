package com.jinuk.toy.infra.redis.lock

import com.jinuk.toy.infra.redis.IntegrationTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.NANOSECONDS

internal class DistributedLockAspectTest(
    private val lockSample: DistributedLockSample,
) : IntegrationTest, DescribeSpec(
    {
        describe("DistributedLock 테스트") {
            it("DistributedLock 을 통한 동시성 제어") {
                lockSample.catchExceptionCountOnConcurrency { concurrency() } shouldBe 0
            }

            it("Lock 이 설정된 점유 시간 보다 길 경우 동시 접근에 대한 예외가 반환되어야 함") {
                lockSample.catchExceptionCountOnConcurrency { leaseTime() } shouldBeGreaterThan 0
            }
        }
    },
)

@Component
internal class DistributedLockSample {
    fun concurrency() =
        distributedLock("DistributedLockSample-concurrency") {
            ConcurrentCounter.increment()
            runBlocking { delay(5) }
            ConcurrentCounter.decrement()
        }

    fun leaseTime() = distributedLock(
        "DistributedLockSample-leaseTime",
        leaseTime = 1,
        timeUnit = NANOSECONDS,
    ) {
        ConcurrentCounter.increment()
        runBlocking { delay(5) }
        ConcurrentCounter.decrement()
    }
}

private fun DistributedLockSample.catchExceptionCountOnConcurrency(block: DistributedLockSample.() -> Unit): Int {
    val latch = CountDownLatch(10)
    var exceptionCount = 0
    Executors.newFixedThreadPool(10).apply {
        repeat(10) {
            execute {
                try {
                    block()
                } catch (e: RuntimeException) {
                    exceptionCount++
                }
                latch.countDown()
            }
        }
    }
    latch.await()
    return exceptionCount
}

private object ConcurrentCounter {
    private var count = 0

    fun increment() {
        if (count >= 1) {
            throw RuntimeException("count is over 1")
        }
        count++
    }

    fun decrement() = count--
}
