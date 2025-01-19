package com.anycommunity.domain.follow.jpa

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.anycommunity.definition.follow.FollowSearchSortType
import com.anycommunity.domain.follow.Follow
import com.anycommunity.infra.mysql.follow.entity.FollowEntity
import com.anycommunity.infra.mysql.follow.jdsl.FollowJdslRepository
import com.anycommunity.infra.mysql.follow.jpa.FollowEntityRepository

@Repository
class FollowRepository(
    private val followEntityRepository: FollowEntityRepository,
    private val followJdslRepository: FollowJdslRepository,
) {
    fun save(follow: Follow) = followEntityRepository.save(follow.toEntity()).toModel()

    fun delete(follow: Follow) = followEntityRepository.delete(follow.toEntity())

    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)?.toModel()

    fun existsByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)

    fun findByFollowingUserId(followingUserId: Long) =
        followEntityRepository.findByFollowingUserId(followingUserId).map { it.toModel() }

    fun search(
        followingUserId: Long? = null,
        followerUserId: Long? = null,
        pageable: Pageable,
        sortType: FollowSearchSortType,
    ) = followJdslRepository.search(
        followingUserId = followingUserId,
        followerUserId = followerUserId,
        pageable = pageable,
        sortType = sortType,
    ).map { it.toModel() }
}

private fun FollowEntity.toModel() = Follow(
    _id = id,
    followerUserId = followerUserId,
    followingUserId = followingUserId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun Follow.toEntity() = FollowEntity(
    id = _id,
    followerUserId = followerUserId,
    followingUserId = followingUserId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
