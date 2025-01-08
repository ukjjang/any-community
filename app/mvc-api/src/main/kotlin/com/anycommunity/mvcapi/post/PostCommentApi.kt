package com.anycommunity.mvcapi.post

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser
import com.anycommunity.mvcapi.post.request.CommentCreateRequest
import com.anycommunity.mvcapi.post.request.CommentUpdateRequest
import com.anycommunity.mvcapi.post.response.PostCommentResponse
import com.anycommunity.usecase.comment.port.command.CommentCommandPort
import com.anycommunity.usecase.comment.port.command.model.DeleteCommentCommand
import com.anycommunity.usecase.comment.port.query.CommentQueryPort
import com.anycommunity.usecase.comment.port.query.model.GetCommentPageQuery
import com.anycommunity.util.custompage.mapToCustomPage

@Tag(name = "게시글 댓글")
@MvcApiController
class PostCommentApi(
    private val commentCommandPort: CommentCommandPort,
    private val commentQueryPort: CommentQueryPort,
) {
    @Operation(summary = "게시글의 댓글 조회")
    @GetMapping("/v1/post/{postId}/comment")
    fun getComments(
        @AuthenticationPrincipal user: AuthUser?,
        @PathVariable postId: Long,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
    ) = GetCommentPageQuery(
        postId = postId,
        viewerId = user?.id,
        page = page,
        size = size,
    ).let {
        commentQueryPort ask it
    }.mapToCustomPage {
        PostCommentResponse.from(it)
    }

    @Operation(summary = "게시글 댓글 작성")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/post/{postId}/comment")
    fun createComment(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @RequestBody request: CommentCreateRequest,
    ) = request.toCommand(postId, user.id).let {
        commentCommandPort execute it
    }

    @Operation(summary = "게시글 댓글 삭제")
    @Secured(AuthRole.USER)
    @DeleteMapping("/v1/post/{postId}/comment/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ) = DeleteCommentCommand(
        userId = user.id,
        postId = postId,
        commentId = commentId,
    ).let { commentCommandPort execute it }

    @Operation(summary = "게시글 댓글 수정")
    @Secured(AuthRole.USER)
    @PutMapping("/v1/post/{postId}/comment/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal user: AuthUser,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentUpdateRequest,
    ) = request.toCommand(
        id = commentId,
        userId = user.id,
        postId = postId,
    ).let { commentCommandPort execute it }
}
