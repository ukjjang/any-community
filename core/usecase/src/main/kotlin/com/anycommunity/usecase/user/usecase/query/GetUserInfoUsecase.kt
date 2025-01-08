package com.anycommunity.usecase.user.usecase.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.user.port.query.model.GetUserInfoQuery
import com.anycommunity.usecase.user.port.query.model.GetUserInfoResult

@Service
class GetUserInfoUsecase(
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetUserInfoQuery) = with(query) {
        userQueryService.getByUsername(username).let { GetUserInfoResult.from(it) }
    }
}
