package com.anycommunity.usecase.user.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.service.UserQueryService

@Service
class GetUserInfoUsecase(
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetUserInfoQuery) = with(query) {
        userQueryService.getByUsername(username).let { GetUserInfoResult.from(it) }
    }
}

data class GetUserInfoQuery(
    val username: Username,
)

data class GetUserInfoResult(
    val id: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(user: User) = with(user) {
            GetUserInfoResult(
                id = id,
                username = username,
                totalPoints = totalPoints,
            )
        }
    }
}
