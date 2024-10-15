package com.jinuk.toy.applicaiton.post.command

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle

data class UpdatePostCommand(
    val userId: Long,
    val id: Long,
    val title: PostTitle,
    val content: String,
)

fun Post.update(
    command: UpdatePostCommand,
): Post {
    require(this.userId == command.userId) {
        "작성자만 게시글을 수정할 수 있습니다."
    }
    return this.copy(
        title = command.title,
        content = command.content,
    )
}
