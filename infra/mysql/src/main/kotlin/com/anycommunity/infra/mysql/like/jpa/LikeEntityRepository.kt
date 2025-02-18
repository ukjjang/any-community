package com.anycommunity.infra.mysql.like.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.definition.like.LikeType
import com.anycommunity.infra.mysql.like.entity.LikeEntity

interface LikeEntityRepository : JpaRepository<LikeEntity, Long> {
    fun findByUserIdAndTargetTypeAndTargetId(userId: Long, targetType: LikeType, targetId: String): LikeEntity?

    fun existsByUserIdAndTargetTypeAndTargetId(userId: Long, targetType: LikeType, targetId: String): Boolean

    fun findByTargetTypeAndTargetId(targetType: LikeType, targetId: String): List<LikeEntity>

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ): List<LikeEntity>

    fun findByTargetTypeAndTargetIdIn(targetType: LikeType, targetId: List<String>): List<LikeEntity>
}
