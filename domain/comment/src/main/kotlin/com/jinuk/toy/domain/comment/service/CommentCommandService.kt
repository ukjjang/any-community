package com.jinuk.toy.domain.comment.service

import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository,
    private val commentQueryService: CommentQueryService,
) {
    fun save(comment: Comment) = commentRepository.save(comment)
    fun delete(id: Long, deleteUserId: Long, parentPostId: Long) =
        commentQueryService.getById(id).apply {
            require(userId == deleteUserId) { "작성자만 게시글을 삭제할 수 있습니다." }
            require(postId == parentPostId) { "해당 댓글은 지정된 게시글에 속하지 않습니다." }
        }.let(commentRepository::delete)

    fun deleteByPostId(postId: Long) = commentRepository.deleteByPostId(postId)
}
