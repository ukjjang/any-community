package com.jinuk.toy.mvcapi.view.post.request

import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentCommand

data class CommentCreateRequest(
    val parentCommentId: Long?,
    val content: String,
)

internal fun CommentCreateRequest.toCommand(
    postId: Long,
    userId: Long,
) = CreateCommentCommand(
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
)
