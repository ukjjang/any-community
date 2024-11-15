package com.jinuk.toy.mvcapi.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.jinuk.toy.applicaiton.follow.command.FollowCommandBus
import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowCommand
import com.jinuk.toy.applicaiton.follow.command.usecase.UnFollowCommand
import com.jinuk.toy.applicaiton.follow.query.FollowQueryBus
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowerQuery
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingQuery
import com.jinuk.toy.applicaiton.user.query.UserQueryBus
import com.jinuk.toy.applicaiton.user.query.usecase.GetUserInfoQuery
import com.jinuk.toy.common.util.custompage.mapToCustomPage
import com.jinuk.toy.common.value.follow.FollowSearchSortType
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.follow.FollowRelation
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.global.security.AuthRole
import com.jinuk.toy.mvcapi.global.security.AuthUser
import com.jinuk.toy.mvcapi.user.response.UserFollowResponse
import com.jinuk.toy.mvcapi.user.response.UserInfoResponse

@Tag(name = "유저")
@MvcAPIController
class UserAPI(
    private val userQueryBus: UserQueryBus,
    private val followQueryBus: FollowQueryBus,
    private val followCommandBus: FollowCommandBus,
) {
    @Operation(summary = "유저 정보")
    @GetMapping("/v1/user/info/{username}")
    fun userInfo(@PathVariable username: Username) = UserInfoResponse.from(
        userQueryBus ask GetUserInfoQuery(username),
    )

    @Operation(summary = "팔로우")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/user/{followingUserId}/follow")
    fun follow(
        @AuthenticationPrincipal user: AuthUser,
        @Parameter(description = "팔로우할 유저의 ID", example = "1")
        @PathVariable followingUserId: Long,
    ) = CreateFollowCommand(FollowRelation(user.id, followingUserId)).let {
        followCommandBus.execute(it)
    }

    @Operation(summary = "언팔로우")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/user/{followingUserId}/follow")
    fun unFollow(
        @AuthenticationPrincipal user: AuthUser,
        @Parameter(description = "언팔로우할 유저의 ID", example = "1")
        @PathVariable followingUserId: Long,
    ) = UnFollowCommand(FollowRelation(user.id, followingUserId)).let {
        followCommandBus.execute(it)
    }

    @GetMapping("/v1/user/following")
    @Secured(AuthRole.USER)
    fun getFollowings(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
        @RequestParam followSearchSortType: FollowSearchSortType = FollowSearchSortType.RECENTLY,
    ) = followQueryBus.ask(GetFollowingQuery(user.id, page, size, followSearchSortType))
        .mapToCustomPage { UserFollowResponse.from(it) }

    @GetMapping("/v1/user/follower")
    @Secured(AuthRole.USER)
    fun getFollowers(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
        @RequestParam followSearchSortType: FollowSearchSortType = FollowSearchSortType.RECENTLY,
    ) = followQueryBus.ask(GetFollowerQuery(user.id, page, size, followSearchSortType))
        .mapToCustomPage { UserFollowResponse.from(it) }
}
