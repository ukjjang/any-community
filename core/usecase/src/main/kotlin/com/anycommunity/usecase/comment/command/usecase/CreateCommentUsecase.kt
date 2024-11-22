package com.anycommunity.usecase.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.domain.comment.Comment
import com.anycommunity.domain.comment.CommentCreateInfo
import com.anycommunity.domain.comment.event.CommentCreatedEvent
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.infra.kafka.service.KafkaProducer
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase

@Service
class CreateCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val kafkaProducer: KafkaProducer,
    private val pointRuleQueryService: PointRuleQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "댓글 작성으로 포인트 지급 | 댓글 ID: "
    }

    @Transactional
    operator fun invoke(command: CreateCommentCommand) {
        val comment = commentCommandService.create(command.toInfo()).also { pointProcess(it) }
        kafkaProducer.send(
            topic = KafkaTopic.Comment.CREATE,
            payload = CommentCreatedEvent.of(comment),
        )
    }

    private fun pointProcess(comment: Comment) {
        val pointRule = pointRuleQueryService.getByRuleType(PointRuleType.COMMENT_CREATION)
        val processCommand = PointProcessCommand(
            userId = comment.userId,
            point = pointRule.amount,
            description = "$POINT_DESCRIPTION_TEMPLATE${comment.id}",
        )
        pointProcessUsecase(processCommand)
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
