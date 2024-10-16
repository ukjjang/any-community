package com.jinuk.toy.domain.user.jpa

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.toEntity
import com.jinuk.toy.domain.user.toModel
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.infra.rdb.user.jpa.UserEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userEntityRepository: UserEntityRepository,
) {
    fun save(user: User) = userEntityRepository.save(user.toEntity()).toModel()

    fun delete(user: User) = userEntityRepository.delete(user.toEntity())

    fun findById(id: Long) = userEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByIdIn(ids: List<Long>): List<User> = userEntityRepository.findAllById(ids).map { it.toModel() }

    fun existsById(id: Long) = userEntityRepository.existsById(id)
    fun existsByUsername(username: Username) = userEntityRepository.existsByUsername(username.value)
    fun findByUsername(username: Username) = userEntityRepository.findByUsername(username.value)?.toModel()
}

