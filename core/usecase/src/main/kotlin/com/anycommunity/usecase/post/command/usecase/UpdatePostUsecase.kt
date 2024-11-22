package com.anycommunity.usecase.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.PostUpdateInfo
import com.anycommunity.domain.post.service.PostCommandService

@Service
class UpdatePostUsecase(
    private val postCommandService: PostCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdatePostCommand) =
        postCommandService.update(command.toInfo()).let { UpdatePostResult.from(it) }
}

data class UpdatePostCommand(
    val userId: Long,
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
) {
    fun toInfo() = PostUpdateInfo(
        id = id,
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
}

data class UpdatePostResult(
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post) = with(post) {
            UpdatePostResult(
                id = id,
                title = title,
                category = category,
                content = content,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
