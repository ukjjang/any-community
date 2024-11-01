package com.jinuk.toy.domain.comment.service

import com.jinuk.toy.domain.comment.jpa.CommentRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CommentQueryService(
    private val commentRepository: CommentRepository,
) {
    fun findById(id: Long) = commentRepository.findById(id)

    fun getById(id: Long) = findById(id) ?: throw NoSuchElementException("존재하지 않는 댓글입니다.")

    fun findByPostIdAndParentCommentIdIsNotNull(postId: Long) =
        commentRepository.findByPostIdAndParentCommentIdIsNotNull(postId)

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(
        postId: Long,
        pageable: Pageable,
    ) = commentRepository.findByPostIdAndParentCommentIdIsNullOrderByIdDesc(
        postId,
        pageable,
    )
}
