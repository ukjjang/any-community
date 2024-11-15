package com.jinuk.toy.domain.point

import java.time.LocalDateTime
import kotlin.random.Random
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.PointGameProbability.Companion.TOTAL_PROBABILITY
import com.jinuk.toy.infra.rdb.point.entity.PointGameProbabilityEntity

data class PointGameProbability(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val point: Point,
    val probability: Int,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointGameProbability) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int = _id?.hashCode() ?: 0

    companion object {
        internal const val TOTAL_PROBABILITY = 10000
    }
}

fun List<PointGameProbability>.pick(): Point {
    val cumulativeProbabilities = this.map { it.probability }
        .runningReduce { acc, prob -> acc + prob }

    require(cumulativeProbabilities.isNotEmpty()) { "확률 리스트가 비어있습니다." }
    require(cumulativeProbabilities.last() == TOTAL_PROBABILITY) { "총 확률 값은 반드시 10000이어야 합니다." }

    val randomValue = Random.nextInt(0, TOTAL_PROBABILITY)
    val selectedIndex = cumulativeProbabilities.indexOfFirst { randomValue < it }
    return this[selectedIndex].point
}

internal fun PointGameProbabilityEntity.toModel() = PointGameProbability(
    _id = id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun PointGameProbability.toEntity() = PointGameProbabilityEntity(
    id = _id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
