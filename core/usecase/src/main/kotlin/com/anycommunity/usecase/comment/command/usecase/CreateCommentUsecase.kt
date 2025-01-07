package com.anycommunity.usecase.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.domain.comment.Comment
import com.anycommunity.domain.comment.CommentCreateInfo
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.infra.redis.lock.timeBoundLock
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase
import com.anycommunity.usecase.post.command.usecase.internal.UpdatePostCommentCountCommand
import com.anycommunity.usecase.post.command.usecase.internal.UpdatePostCommentCountUseCase

@Service
class CreateCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
    private val updatePostCommentCountUseCase: UpdatePostCommentCountUseCase,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "댓글 작성으로 포인트 지급 | 댓글 ID: "
    }

    @Transactional
    operator fun invoke(command: CreateCommentCommand) {
        commentCommandService.create(command.toInfo())
            .also {
                updatePostCommentCount(it)

                timeBoundLock(
                    key = "CreateCommentUsecase:pointProcess:${command.userId}",
                    leaseTime = 10,
                    timeUnit = TimeUnit.MINUTES,
                ) {
                    pointProcess(it)
                }
            }
    }

    private fun updatePostCommentCount(comment: Comment) {
        val command = UpdatePostCommentCountCommand(
            postId = comment.postId,
            countOperation = CountOperation.INCREASE,
        )
        updatePostCommentCountUseCase(command)
    }

    private fun pointProcess(comment: Comment) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.COMMENT_CREATION)
        val command = PointProcessCommand(
            userId = comment.userId,
            point = pointRule.amount,
            description = "$POINT_DESCRIPTION_TEMPLATE${comment.id}",
        )
        pointProcessUsecase(command)
    }
}

data class CreateCommentCommand(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val content: String,
) {
    fun toInfo() = CommentCreateInfo(
        userId = userId,
        postId = postId,
        parentCommentId = parentCommentId,
        content = content,
    )
}
