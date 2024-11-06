package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.constant.post.PostCategory
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.post.value.PostTitle

@Service
class CreatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService,
) {
    @Transactional
    operator fun invoke(command: CreatePostCommand): Post {
        require(!postQueryService.existsByTitle(command.title.value)) { "이미 존재하는 게시글 제목입니다." }
        return postCommandService.save(command.toPost())
    }
}

data class CreatePostCommand(
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
)

private fun CreatePostCommand.toPost() =
    Post(
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
