package com.jinuk.toy.infra.rdb.comment.jpa

import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentEntityRepository : JpaRepository<CommentEntity, Long> {
    fun findByUserIdAndPostId(
        userId: Long,
        postId: Long,
    ): List<CommentEntity>

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(
        postId: Long,
        pageable: Pageable,
    ): Page<CommentEntity>

    fun findByPostId(postId: Long): List<CommentEntity>

    fun findByPostIdAndParentCommentIdIsNotNull(postId: Long): List<CommentEntity>

    fun countByPostId(postId: Long): Int

    fun findByPostIdIn(postIds: List<Long>): List<CommentEntity>

    fun deleteByPostId(postId: Long)
}
