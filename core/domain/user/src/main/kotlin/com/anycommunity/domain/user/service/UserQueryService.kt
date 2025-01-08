package com.anycommunity.domain.user.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.util.custompage.toCustomPage

@Service
class UserQueryService(
    private val userRepository: UserRepository,
) {
    fun findByIdIn(id: List<Long>) = userRepository.findByIdIn(id)

    fun existsById(id: Long) = userRepository.existsById(id)

    fun getById(id: Long) = userRepository.findById(id) ?: throw NoSuchElementException("존재하지 않는 유저입니다.")

    fun findByUsername(username: Username) = userRepository.findByUsername(username)

    fun getByUsername(username: Username) = findByUsername(username) ?: throw NoSuchElementException("존재하지 않는 유저입니다.")

    fun getPointRankingUser(pageable: Pageable) = userRepository.findByOrderByTotalPointsDesc(pageable).toCustomPage()
}
