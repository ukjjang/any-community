package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.comment.command.usecase.UpdateCommentCommand

data class CommentUpdateRequest(
    val content: String,
)

internal fun CommentUpdateRequest.toCommand(id: Long, postId: Long, userId: Long) = UpdateCommentCommand(
    id = id,
    userId = userId,
    postId = postId,
    content = content,
)
