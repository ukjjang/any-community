package com.anycommunity.domain.user.service

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.jpa.UserRepository

@Service
class UserCommandService(
    private val userQueryService: UserQueryService,
    private val userRepository: UserRepository,
) {
    fun save(post: User) = userRepository.save(post)

    fun updateFollowingCount(useId: Long, countOperation: CountOperation) = save(
        userQueryService.getById(useId).updateFollowingCount(countOperation),
    )

    fun updateFollowerCount(useId: Long, countOperation: CountOperation) = save(
        userQueryService.getById(useId).updateFollowerCount(countOperation),
    )

    fun updateTotalPoints(useId: Long, point: Point) = save(
        userQueryService.getById(useId).updateTotalPoints(point),
    )
}
