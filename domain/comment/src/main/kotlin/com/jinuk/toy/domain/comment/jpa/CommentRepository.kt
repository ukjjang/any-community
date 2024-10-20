package com.jinuk.toy.domain.comment.jpa

import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.toEntity
import com.jinuk.toy.domain.comment.toModel
import com.jinuk.toy.infra.rdb.comment.jpa.CommentEntityRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentRepository(
    private val commentEntityRepository: CommentEntityRepository,
) {
    fun save(comment: Comment) = commentEntityRepository.save(comment.toEntity()).toModel()

    fun delete(comment: Comment) = commentEntityRepository.delete(comment.toEntity())

    fun deleteByPostId(postId: Long) = commentEntityRepository.deleteByPostId(postId)

    fun findById(id: Long) = commentEntityRepository.findByIdOrNull(id)?.toModel()

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(
        postId: Long,
        pageable: Pageable,
    ) = commentEntityRepository.findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId, pageable).map { it.toModel() }

    fun findByPostId(postId: Long) = commentEntityRepository.findByPostId(postId).map { it.toModel() }

    fun countByPostId(postId: Long) = commentEntityRepository.countByPostId(postId)

    fun findByPostIdIn(postIds: List<Long>) = commentEntityRepository.findByPostIdIn(postIds).map { it.toModel() }

    fun findByIdIn(ids: List<Long>): List<Comment> = commentEntityRepository.findAllById(ids).map { it.toModel() }

    fun findByUserIdAndPostId(
        userId: Long,
        postId: Long,
    ) = commentEntityRepository.findByUserIdAndPostId(userId, postId).map { it.toModel() }
}
