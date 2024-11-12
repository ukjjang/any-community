package com.jinuk.toy.domain.user.service

import org.springframework.stereotype.Service
import com.jinuk.toy.common.define.global.CountOperation
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.jpa.UserRepository

@Service
class UserCommandService(
    private val userQueryService: UserQueryService,
    private val userRepository: UserRepository,
) {
    fun save(post: User) = userRepository.save(post)

    fun updateFollowingCount(
        useId: Long,
        countOperation: CountOperation,
    ) = save(
        userQueryService.getById(useId).updateFollowingCount(countOperation),
    )

    fun updateFollowerCount(
        useId: Long,
        countOperation: CountOperation,
    ) = save(
        userQueryService.getById(useId).updateFollowerCount(countOperation),
    )
}
