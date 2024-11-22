package com.anycommunity.mvcapi.like

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser
import com.anycommunity.usecase.like.command.LikeCommandBus
import com.anycommunity.usecase.like.command.usecase.AddLikeCommand
import com.anycommunity.usecase.like.command.usecase.CancelLikeCommand

@Tag(name = "좋아요")
@MvcApiController
class LikeApi(
    private val likeCommandBus: LikeCommandBus,
) {
    @Operation(summary = "좋아요 추가")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/like/{targetType}/{targetId}")
    fun addLike(
        @AuthenticationPrincipal user: AuthUser,
        @Parameter(description = "좋아요 대상 타입", example = "POST")
        @PathVariable targetType: LikeType,
        @Parameter(description = "좋아요 대상 ID", example = "1")
        @PathVariable targetId: String,
    ) = AddLikeCommand(
        likeTarget = LikeTarget(targetType, targetId),
        userId = user.id,
    ).let {
        likeCommandBus execute it
    }

    @Operation(summary = "좋아요 취소")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/like/{targetType}/{targetId}")
    fun cancelLike(
        @AuthenticationPrincipal user: AuthUser,
        @Parameter(description = "좋아요 대상 타입", example = "POST")
        @PathVariable targetType: LikeType,
        @Parameter(description = "좋아요 대상 ID", example = "1")
        @PathVariable targetId: String,
    ) = CancelLikeCommand(
        likeTarget = LikeTarget(targetType, targetId),
        userId = user.id,
    ).let {
        likeCommandBus execute it
    }
}
