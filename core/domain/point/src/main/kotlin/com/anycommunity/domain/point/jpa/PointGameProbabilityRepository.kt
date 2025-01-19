package com.anycommunity.domain.point.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.domain.point.PointGameProbability
import com.anycommunity.infra.mysql.point.entity.PointGameProbabilityEntity
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

private fun PointGameProbabilityEntity.toModel() = PointGameProbability(
    _id = id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun PointGameProbability.toEntity() = PointGameProbabilityEntity(
    id = _id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
