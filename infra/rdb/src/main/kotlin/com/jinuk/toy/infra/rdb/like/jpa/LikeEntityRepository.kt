package com.jinuk.toy.infra.rdb.like.jpa

import com.jinuk.toy.infra.rdb.like.entity.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

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
}
