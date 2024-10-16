package com.jinuk.toy.externalapi.view.follow

import com.jinuk.toy.applicaiton.follow.command.FollowCommandBus
import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowCommand
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.externalapi.global.security.AuthRole
import com.jinuk.toy.externalapi.global.security.AuthUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "팔로우")
@RequestMapping("/follow")
@RestController
class FollowAPI(
    private val followCommandBus: FollowCommandBus
) {

    @Operation(summary = "팔로우")
    @Secured(AuthRole.USER)
    @PostMapping("/{followingUserId}")
    fun follow(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable followingUserId: Long,
    ) = CreateFollowCommand(FollowRelation(user.id, followingUserId)).let {
        followCommandBus.execute(it)
    }
}
