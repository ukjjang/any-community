package com.jinuk.toy.domain.like.jpa

import org.springframework.stereotype.Repository
import com.jinuk.toy.common.value.like.LikeType
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

    fun deleteAll(likes: List<Like>) = likeEntityRepository.deleteAll(likes.map { it.toEntity() })

    fun findByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeEntityRepository.findByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)?.toModel()

    fun existsByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeEntityRepository.existsByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)

    fun findByTargetTypeAndTargetId(target: LikeTarget) =
        likeEntityRepository.findByTargetTypeAndTargetId(target.type, target.id).map { it.toModel() }

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ) = likeEntityRepository.findByUserIdAndTargetTypeAndTargetIdIn(userId, targetType, targetId.toList()).map {
        it.toModel()
    }

    fun findByTargetTypeAndTargetIdIn(
        targetType: LikeType,
        targetId: List<Long>,
    ) = likeEntityRepository.findByTargetTypeAndTargetIdIn(targetType, targetId.map { it.toString() }).map {
        it.toModel()
    }
}
