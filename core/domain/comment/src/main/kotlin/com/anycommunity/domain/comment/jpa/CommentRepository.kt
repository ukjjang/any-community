package com.anycommunity.domain.comment.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.domain.comment.Comment
import com.anycommunity.infra.mysql.comment.entity.CommentEntity
import com.anycommunity.infra.mysql.comment.jpa.CommentEntityRepository
import com.anycommunity.util.custompage.toCustomPage

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

private fun CommentEntity.toModel() = Comment(
    _id = id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun Comment.toEntity() = CommentEntity(
    id = _id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
