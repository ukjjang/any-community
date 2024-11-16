package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessCommand
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessUsecase
import com.jinuk.toy.common.value.global.kafka.KafkaTopic
import com.jinuk.toy.common.value.point.PointRuleType
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.event.CommentCreatedEvent
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.point.service.PointRuleQueryService
import com.jinuk.toy.infra.kafka.service.KafkaProducer

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
    operator fun invoke(command: CreateCommentCommand) = with(command) {
        val comment = Comment.create(
            userId = userId,
            postId = postId,
            parentCommentId = parentCommentId,
            content = content,
        ).let {
            commentCommandService.save(it)
        }.also {
            pointProcess(it)
        }

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
)
