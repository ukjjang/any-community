package com.jinuk.toy.applicaiton.post.command.usecase

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.post.value.PostTitle
import org.springframework.stereotype.Service

@Service
class UpdatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService,
) {

    operator fun invoke(command: UpdatePostCommand): Post {
        require(!postQueryService.existsByTitle(command.title.value)) { "이미 존재하는 게시글 제목입니다." }
        val post = postQueryService.getById(command.id)
        val updatedPost = post.update(command)
        return postCommandService.save(updatedPost)
    }
}

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
