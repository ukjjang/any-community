package com.jinuk.toy.domain.point.service

import org.springframework.stereotype.Service
import java.time.Duration
import com.jinuk.toy.domain.point.jpa.PointGameProbabilityRepository
import com.jinuk.toy.infra.redis.cache.cached

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
