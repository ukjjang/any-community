package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessCommand
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessUsecase
import com.jinuk.toy.common.value.point.PointRuleType
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.point.service.PointRuleQueryService
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.PostCreateInfo
import com.jinuk.toy.domain.post.service.PostCommandService

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
    operator fun invoke(command: CreatePostCommand) =
        postCommandService.create(command.toInfo()).also { pointProcess(it) }

    private fun pointProcess(post: Post) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.POST_CREATION)
        val processCommand =
            PointProcessCommand(
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
