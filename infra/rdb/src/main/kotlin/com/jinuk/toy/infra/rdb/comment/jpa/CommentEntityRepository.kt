package com.jinuk.toy.infra.rdb.comment.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity

interface CommentEntityRepository : JpaRepository<CommentEntity, Long> {
    fun findByUserIdAndPostId(userId: Long, postId: Long): List<CommentEntity>

    fun findByPostIdAndParentCommentIdIsNullOrderByIdDesc(postId: Long, pageable: Pageable): Page<CommentEntity>

    fun findByPostId(postId: Long): List<CommentEntity>

    fun findByPostIdAndParentCommentIdIsNotNull(postId: Long): List<CommentEntity>
}
