package com.jinuk.toy.infra.rdb.like.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.like.entity.LikeEntity

interface LikeEntityRepository : JpaRepository<LikeEntity, Long> {
    fun findByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        targetType: String,
        targetId: String,
    ): LikeEntity?

    fun existsByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        targetType: String,
        targetId: String,
    ): Boolean

    fun countByTargetTypeAndTargetId(
        targetType: String,
        targetId: String,
    ): Int

    fun findByTargetTypeAndTargetIdIn(
        targetType: String,
        targetId: List<String>,
    ): List<LikeEntity>

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: String,
        targetId: List<String>,
    ): List<LikeEntity>
}
