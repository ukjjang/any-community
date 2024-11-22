package com.anycommunity.usecase.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.PostCreateInfo
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase

@Service
class CreatePostUsecase(
    private val postCommandService: PostCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "게시글 작성으로 포인트 지급 | 게시글 ID: "
    }

    @Transactional
    operator fun invoke(command: CreatePostCommand): CreatePostResult {
        val post = postCommandService.create(command.toInfo()).also { pointProcess(it) }
        return CreatePostResult.from(post)
    }

    private fun pointProcess(post: Post) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.POST_CREATION)
        val processCommand = PointProcessCommand(
            userId = post.userId,
            point = pointRule.amount,
            description = "$POINT_DESCRIPTION_TEMPLATE${post.id}",
        )
        pointProcessUsecase(processCommand)
    }
}

data class CreatePostCommand(
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
) {
    fun toInfo() = PostCreateInfo(
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
}

data class CreatePostResult(
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post) = with(post) {
            CreatePostResult(
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
