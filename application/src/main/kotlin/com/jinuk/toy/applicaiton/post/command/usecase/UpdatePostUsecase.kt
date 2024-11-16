package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.domain.post.service.PostQueryService

@Service
class UpdatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdatePostCommand) {
        val post = postQueryService.getById(command.id)
        validate(post, command)
        post.update(
            title = command.title,
            category = command.category,
            content = command.content,
        ).also { postCommandService.save(it) }
    }

    private fun validate(post: Post, command: UpdatePostCommand) {
        require(post.title == command.title || !postQueryService.existsByTitle(command.title)) {
            "이미 존재하는 게시글 제목입니다."
        }
        require(post.userId == command.userId) {
            "작성자만 게시글을 수정할 수 있습니다."
        }
    }
}

data class UpdatePostCommand(
    val userId: Long,
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
)
