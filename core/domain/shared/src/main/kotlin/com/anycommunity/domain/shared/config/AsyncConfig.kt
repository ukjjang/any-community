package com.anycommunity.domain.shared.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
@EnableAsync
class AsyncConfig {

    companion object {
        const val OUTBOX_POOL = "outbox-process-pool"
    }

    @Bean(name = [OUTBOX_POOL])
    fun outboxProcessPool(): ExecutorService = Executors.newVirtualThreadPerTaskExecutor()
}
