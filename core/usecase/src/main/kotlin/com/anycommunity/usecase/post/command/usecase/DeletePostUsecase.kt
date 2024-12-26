package com.anycommunity.usecase.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.comment.service.CommentQueryService
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.post.event.PostDeletedEvent
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator

@Service
class DeletePostUsecase(
    private val postCommandService: PostCommandService,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val likeCommandService: LikeCommandService,
    private val outboxCreator: OutboxCreator,
) {
    @Transactional
    operator fun invoke(command: DeletePostCommand) = with(command) {
        postCommandService.delete(id, userId)
        likeCommandService.delete(LikeTarget.from(LikeType.POST, id))

        val comments = commentQueryService.findByPostId(id)
        commentCommandService.deleteAll(comments)
        likeCommandService.delete(LikeType.COMMENT, comments.map { it.id })

        outboxCreator.create(KafkaTopic.Post.DELETE, PostDeletedEvent(id))
    }
}

data class DeletePostCommand(val userId: Long, val id: Long)
