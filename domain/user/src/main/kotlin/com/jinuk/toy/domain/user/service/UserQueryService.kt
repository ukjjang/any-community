package com.jinuk.toy.domain.user.service

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.user.jpa.UserJdslRepository
import com.jinuk.toy.domain.user.jpa.UserRepository

@Service
class UserQueryService(
    private val userRepository: UserRepository,
    private val userJdslRepository: UserJdslRepository,
) {
    fun findByIdIn(id: List<Long>) = userRepository.findByIdIn(id)

    fun existsById(id: Long) = userRepository.existsById(id)

    fun getById(id: Long) = userRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 유저입니다.")

    fun findByUsername(username: Username) = userRepository.findByUsername(username)

    fun getByUsername(username: Username) = findByUsername(username) ?: throw NoSuchElementException("존재하지 않는 유저입니다.")

    fun getPointRankingUser(limit: Int) = userJdslRepository.getPointRankingUser(limit)
}
