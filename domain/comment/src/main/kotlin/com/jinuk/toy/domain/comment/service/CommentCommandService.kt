package com.jinuk.toy.domain.comment.service

import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository,
) {
    fun save(comment: Comment) = commentRepository.save(comment)
}
