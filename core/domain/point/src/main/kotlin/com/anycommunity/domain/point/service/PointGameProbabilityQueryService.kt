package com.anycommunity.domain.point.service

import org.springframework.stereotype.Service
import java.time.Duration
import com.anycommunity.domain.point.jpa.PointGameProbabilityRepository
import com.anycommunity.infra.redis.cache.cached

@Service
class PointGameProbabilityQueryService(
    private val pointGameProbabilityRepository: PointGameProbabilityRepository,
) {
    fun findAll() = cached(
        key = "PointGameProbabilityQueryService:findAll",
        expire = Duration.ofHours(3),
    ) {
        pointGameProbabilityRepository.findAll()
    }
}
