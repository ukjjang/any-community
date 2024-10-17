package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentCommand

data class CommentCreateRequest(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val content: String,
)

internal fun CommentCreateRequest.toCommand(userId: Long) = CreateCommentCommand(
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
)
