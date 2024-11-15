package com.jinuk.toy.domain.comment.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.jinuk.toy.common.util.custompage.toCustomPage
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.toEntity
import com.jinuk.toy.domain.comment.toModel
import com.jinuk.toy.infra.rdb.comment.jpa.CommentEntityRepository

@Repository
class CommentRepository(
    private val commentEntityRepository: CommentEntityRepository,
) {
    fun save(comment: Comment) = commentEntityRepository.save(comment.toEntity()).toModel()

    fun delete(comment: Comment) = commentEntityRepository.delete(comment.toEntity())

    fun deleteAll(comments: List<Comment>) = commentEntityRepository.deleteAll(comments.map { it.toEntity() })

    fun findById(id: Long) = commentEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId: Long, pageable: Pageable) = commentEntityRepository
        .findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId, pageable)
        .map { it.toModel() }
        .toCustomPage()

    fun findByPostId(postId: Long) = commentEntityRepository.findByPostId(postId).map { it.toModel() }

    fun findByPostIdAndParentCommentIdIsNotNull(postId: Long) =
        commentEntityRepository.findByPostIdAndParentCommentIdIsNotNull(postId).map { it.toModel() }

    fun findByUserIdAndPostId(userId: Long, postId: Long) =
        commentEntityRepository.findByUserIdAndPostId(userId, postId).map { it.toModel() }
}
