package com.anycommunity.domain.user.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User
import com.anycommunity.infra.mysql.user.entity.UserEntity
import com.anycommunity.infra.mysql.user.jpa.UserEntityRepository

@Repository
class UserRepository(
    private val userEntityRepository: UserEntityRepository,
) {
    fun save(user: User) = userEntityRepository.save(user.toEntity()).toModel()

    fun findById(id: Long) = userEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<User> = userEntityRepository.findAllById(ids).map { it.toModel() }

    fun existsById(id: Long) = userEntityRepository.existsById(id)

    fun existsByUsername(username: Username) = userEntityRepository.existsByUsername(username)

    fun findByUsername(username: Username) = userEntityRepository.findByUsername(username)?.toModel()

    fun findByOrderByTotalPointsDesc(pageable: Pageable) =
        userEntityRepository.findByOrderByTotalPointsDesc(pageable).map { it.toModel() }

    fun deleteAll() = userEntityRepository.deleteAll()
}

private fun UserEntity.toModel() = User(
    _id = id,
    username = username,
    password = password,
    gender = gender,
    totalPoints = totalPoints,
    followingCount = followingCount,
    followerCount = followerCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun User.toEntity() = UserEntity(
    id = _id,
    username = username,
    password = password,
    gender = gender,
    totalPoints = totalPoints,
    followingCount = followingCount,
    followerCount = followerCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
