package com.jinuk.toy.infra.rdb.like.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.constant.like.LikeType
import com.jinuk.toy.infra.rdb.like.entity.LikeEntity

interface LikeEntityRepository : JpaRepository<LikeEntity, Long> {
    fun findByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        targetType: LikeType,
        targetId: String,
    ): LikeEntity?

    fun existsByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        targetType: LikeType,
        targetId: String,
    ): Boolean

    fun countByTargetTypeAndTargetId(
        targetType: LikeType,
        targetId: String,
    ): Int

    fun findByTargetTypeAndTargetIdIn(
        targetType: LikeType,
        targetId: List<String>,
    ): List<LikeEntity>

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ): List<LikeEntity>
}
