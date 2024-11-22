package com.anycommunity.domain.comment.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.domain.comment.jpa.CommentRepository

@Service
class CommentQueryService(
    private val commentRepository: CommentRepository,
) {
    fun findById(id: Long) = commentRepository.findById(id)

    fun getById(id: Long) = findById(id) ?: throw NoSuchElementException("존재하지 않는 댓글입니다.")

    fun findByPostId(postId: Long) = commentRepository.findByPostId(postId)

    fun findByPostIdAndParentCommentIdIsNotNull(postId: Long) =
        commentRepository.findByPostIdAndParentCommentIdIsNotNull(postId)

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId: Long, pageable: Pageable) =
        commentRepository.findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId, pageable)
}
