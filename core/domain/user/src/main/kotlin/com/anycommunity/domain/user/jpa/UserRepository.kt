package com.anycommunity.domain.user.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.toEntity
import com.anycommunity.domain.user.toModel
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

    fun deleteAll() = userEntityRepository.deleteAll()
}
