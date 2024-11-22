package com.anycommunity.domain.comment.service

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.comment.Comment
import com.anycommunity.domain.comment.CommentCreateInfo
import com.anycommunity.domain.comment.CommentUpdateInfo
import com.anycommunity.domain.comment.jpa.CommentRepository

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository,
    private val commentQueryService: CommentQueryService,
) {
    fun save(comment: Comment) = commentRepository.save(comment)

    fun create(info: CommentCreateInfo) = save(Comment.create(info = info))

    fun update(info: CommentUpdateInfo) = commentQueryService.getById(info.id)
        .let { commentRepository.save(it.update(info)) }

    fun delete(id: Long, deleteUserId: Long, parentPostId: Long) {
        val comment = commentQueryService.getById(id)
        require(comment.userId == deleteUserId) { "작성자만 게시글을 삭제할 수 있습니다." }
        require(comment.postId == parentPostId) { "해당 댓글은 지정된 게시글에 속하지 않습니다." }
        commentRepository.delete(comment)
    }

    fun deleteAll(comments: List<Comment>) = commentRepository.deleteAll(comments)

    fun updateLikeCount(commentId: Long, countOperation: CountOperation) = save(
        commentQueryService.getById(commentId).updateLikeCount(countOperation),
    )
}
