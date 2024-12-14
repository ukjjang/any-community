package com.anycommunity.consumer.batch

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import com.anycommunity.domain.shared.outbox.OutboxRetryHandler
import com.anycommunity.infra.redis.lock.executeIfAcquired

// 서비스 규모가 아직 작고, 배치 로직 하나를 처리하기 위해 배치 서버를 별도로 구성하는 것은 투 머치하다고 판단,
// 임시적으로 컨슈머 모듈 내에서 스케줄러 방식으로 배치 작업을 처리
@EnableScheduling
@Component
class OutboxRetryScheduler(
    private val outboxRetryHandler: OutboxRetryHandler,
) {

    @Scheduled(fixedRate = 600000) // 10분마다 실행
    fun retry() {
        // 서버가 여러대인 경우 스케줄러가 여러 번 실행될 수 있어, 한 번만 실행되도록 제한
        executeIfAcquired(key = "outbox-retry", leaseTime = 60, transactional = true) {
            outboxRetryHandler.retry()
        }
    }
}
