package com.jinuk.toy.domain.user.jpa

import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.toEntity
import com.jinuk.toy.domain.user.toModel
import com.jinuk.toy.infra.rdb.follow.jpa.FollowEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class FollowRepository(
    private val followEntityRepository: FollowEntityRepository,
) {
    fun save(user: Follow) = followEntityRepository.save(user.toEntity()).toModel()

    fun delete(user: Follow) = followEntityRepository.delete(user.toEntity())

    fun findById(id: Long) = followEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<Follow> = followEntityRepository.findAllById(ids).map { it.toModel() }

    fun findByFollowerUserId(followerUserId: Long) =
        followEntityRepository.findByFollowerUserId(followerUserId).map { it.toModel() }

    fun findByFollowingUserId(followingUserId: Long) =
        followEntityRepository.findByFollowingUserId(followingUserId).map { it.toModel() }

    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)?.toModel()

    fun existsByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
}

