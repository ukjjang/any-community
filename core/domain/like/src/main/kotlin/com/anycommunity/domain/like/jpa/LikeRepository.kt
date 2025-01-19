package com.anycommunity.domain.like.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.Like
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.infra.mysql.like.entity.LikeEntity
import com.anycommunity.infra.mysql.like.jpa.LikeEntityRepository

@Repository
class LikeRepository(
    private val likeEntityRepository: LikeEntityRepository,
) {
    fun save(like: Like) = likeEntityRepository.save(like.toEntity()).toModel()

    fun delete(like: Like) = likeEntityRepository.delete(like.toEntity())

    fun deleteAll(likes: List<Like>) = likeEntityRepository.deleteAll(likes.map { it.toEntity() })

    fun findByUserIdAndTarget(userId: Long, target: LikeTarget) =
        likeEntityRepository.findByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)?.toModel()

    fun existsByUserIdAndTarget(userId: Long, target: LikeTarget) =
        likeEntityRepository.existsByUserIdAndTargetTypeAndTargetId(userId, target.type, target.id)

    fun findByTargetTypeAndTargetId(target: LikeTarget) =
        likeEntityRepository.findByTargetTypeAndTargetId(target.type, target.id).map { it.toModel() }

    fun findByUserIdAndTargetTypeAndTargetIdIn(userId: Long, targetType: LikeType, targetId: List<String>) =
        likeEntityRepository.findByUserIdAndTargetTypeAndTargetIdIn(userId, targetType, targetId.toList()).map {
            it.toModel()
        }

    fun findByTargetTypeAndTargetIdIn(targetType: LikeType, targetId: List<Long>) =
        likeEntityRepository.findByTargetTypeAndTargetIdIn(targetType, targetId.map { it.toString() }).map {
            it.toModel()
        }
}

private fun LikeEntity.toModel() = Like(
    _id = id,
    userId = userId,
    targetType = targetType,
    targetId = targetId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun Like.toEntity() = LikeEntity(
    id = _id,
    userId = userId,
    targetType = targetType,
    targetId = targetId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
