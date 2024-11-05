package com.jinuk.toy.domain.like.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.jinuk.toy.constant.like.LikeType
import com.jinuk.toy.domain.like.Like
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.toEntity
import com.jinuk.toy.domain.like.toModel
import com.jinuk.toy.infra.rdb.like.jpa.LikeEntityRepository

@Repository
class LikeRepository(
    private val likeEntityRepository: LikeEntityRepository,
) {
    fun save(like: Like) = likeEntityRepository.save(like.toEntity()).toModel()

    fun delete(like: Like) = likeEntityRepository.delete(like.toEntity())

    fun findById(id: Long) = likeEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<Like> = likeEntityRepository.findAllById(ids).map { it.toModel() }

    fun findByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeEntityRepository.findByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)?.toModel()

    fun existsByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeEntityRepository.existsByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)

    fun countByTarget(target: LikeTarget) =
        likeEntityRepository.countByTargetTypeAndTargetId(
            target.type,
            target.id,
        )

    fun findByTargetTypeAndTargetIdIn(
        targetType: LikeType,
        targetId: List<String>,
    ) = likeEntityRepository.findByTargetTypeAndTargetIdIn(targetType, targetId.toList()).map { it.toModel() }

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ) = likeEntityRepository.findByUserIdAndTargetTypeAndTargetIdIn(userId, targetType, targetId.toList()).map {
        it.toModel()
    }
}
