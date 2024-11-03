package com.jinuk.toy.mvcapi.view.like

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import com.jinuk.toy.applicaiton.like.command.LikeCommandBus
import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.CancelLikeCommand
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.global.security.AuthRole
import com.jinuk.toy.mvcapi.global.security.AuthUser

@Tag(name = "좋아요")
@MvcAPIController
class LikeAPI(
    private val likeCommandBus: LikeCommandBus,
) {
    @Operation(summary = "좋아요 추가")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/like/{targetType}/{targetId}")
    fun addLike(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable targetType: LikeType,
        @PathVariable targetId: String,
    ) = AddLikeCommand(
        likeTarget = LikeTarget(targetType, targetId),
        userId = user.id,
    ).let {
        likeCommandBus.execute(it)
    }

    @Operation(summary = "좋아요 취소")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/like/{targetType}/{targetId}")
    fun cancelLike(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable targetType: LikeType,
        @PathVariable targetId: String,
    ) = CancelLikeCommand(
        likeTarget = LikeTarget(targetType, targetId),
        userId = user.id,
    ).let {
        likeCommandBus.execute(it)
    }
}
