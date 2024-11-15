package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.value.like.LikeType
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.comment.service.CommentQueryService
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService
import com.jinuk.toy.domain.post.service.PostCommandService

@Service
class DeletePostUsecase(
    private val postCommandService: PostCommandService,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val likeCommandService: LikeCommandService,
) {
    @Transactional
    operator fun invoke(command: DeletePostCommand) = with(command) {
        postCommandService.delete(id, userId)
        likeCommandService.delete(LikeTarget.from(LikeType.POST, id))

        val comments = commentQueryService.findByPostId(id)
        commentCommandService.deleteAll(comments)
        likeCommandService.delete(LikeType.COMMENT, comments.map { it.id })
    }
}

data class DeletePostCommand(val userId: Long, val id: Long)
