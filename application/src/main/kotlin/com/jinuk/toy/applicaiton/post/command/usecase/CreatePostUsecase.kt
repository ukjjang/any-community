package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.applicaiton.point.command.PointCommandBus
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessCommand
import com.jinuk.toy.common.value.point.PointRuleType
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.point.service.PointRuleQueryService
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.domain.post.service.PostQueryService

@Service
class CreatePostUsecase(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val pointCommandBus: PointCommandBus,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "게시글 작성으로 포인트 지급 | 게시글 ID: "
    }

    @Transactional
    operator fun invoke(command: CreatePostCommand): Post {
        require(!postQueryService.existsByTitle(command.title)) { "이미 존재하는 게시글 제목입니다." }
        return postCommandService.save(command.toPost()).also { pointProcess(it) }
    }

    private fun pointProcess(post: Post) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.POST_CREATION)
        val processCommand =
            PointProcessCommand(
                userId = post.userId,
                point = pointRule.amount,
                description = "$POINT_DESCRIPTION_TEMPLATE${post.id}",
            )
        pointCommandBus execute processCommand
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
