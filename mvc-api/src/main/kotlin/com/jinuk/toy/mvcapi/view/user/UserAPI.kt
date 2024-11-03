package com.jinuk.toy.mvcapi.view.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import com.jinuk.toy.applicaiton.follow.command.FollowCommandBus
import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowCommand
import com.jinuk.toy.applicaiton.follow.command.usecase.UnFollowCommand
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.global.security.AuthRole
import com.jinuk.toy.mvcapi.global.security.AuthUser

@Tag(name = "유저")
@MvcAPIController
class UserAPI(
    private val followCommandBus: FollowCommandBus,
) {
    @Operation(summary = "팔로우")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/user/{followingUserId}/follow")
    fun follow(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable followingUserId: Long,
    ) = CreateFollowCommand(FollowRelation(user.id, followingUserId)).let {
        followCommandBus.execute(it)
    }

    @Operation(summary = "언팔로우")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/user/{followingUserId}/follow")
    fun unFollow(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable followingUserId: Long,
    ) = UnFollowCommand(FollowRelation(user.id, followingUserId)).let {
        followCommandBus.execute(it)
    }
}
