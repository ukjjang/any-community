package com.anycommunity.domain.point.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.domain.point.PointGameProbability
import com.anycommunity.domain.point.toEntity
import com.anycommunity.domain.point.toModel
import com.anycommunity.infra.mysql.point.jpa.PointGameProbabilityEntityRepository

@Repository
class PointGameProbabilityRepository(
    private val pointGameProbabilityEntityRepository: PointGameProbabilityEntityRepository,
) {
    fun save(pointGameProbability: PointGameProbability) =
        pointGameProbabilityEntityRepository.save(pointGameProbability.toEntity()).toModel()

    fun delete(pointGameProbability: PointGameProbability) =
        pointGameProbabilityEntityRepository.delete(pointGameProbability.toEntity())

    fun findAll() = pointGameProbabilityEntityRepository.findAll().map { it.toModel() }
}
