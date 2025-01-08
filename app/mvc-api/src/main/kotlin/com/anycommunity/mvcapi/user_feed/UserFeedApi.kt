package com.anycommunity.mvcapi.user_feed

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser
import com.anycommunity.mvcapi.user_feed.response.UserFeedResponse
import com.anycommunity.usecase.user_feed.port.query.UserFeedQueryPort
import com.anycommunity.usecase.user_feed.port.query.model.GetUserFeedQuery
import com.anycommunity.util.custompage.mapToCustomPage

@Tag(name = "유저 피드")
@MvcApiController
class UserFeedApi(
    private val userFeedQueryPort: UserFeedQueryPort,
) {
    @Operation(summary = "유저 피드 조회")
    @Secured(AuthRole.USER)
    @GetMapping("/v1/user-feed")
    fun getUserFeeds(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
    ) = GetUserFeedQuery(user.id, page, size).let {
        userFeedQueryPort ask it
    }.mapToCustomPage { UserFeedResponse.from(it) }
}
