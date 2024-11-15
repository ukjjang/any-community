package com.jinuk.toy.domain.point.jpa

import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.point.PointGameProbability
import com.jinuk.toy.domain.point.toEntity
import com.jinuk.toy.domain.point.toModel
import com.jinuk.toy.infra.rdb.point.jpa.PointGameProbabilityEntityRepository

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
