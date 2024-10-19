package com.jinuk.toy.infra.rdb.comment.jpa

import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentEntityRepository : JpaRepository<CommentEntity, Long> {
    fun findByUserIdAndPostId(
        userId: Long,
        postId: Long,
    ): List<CommentEntity>

    fun findByPostId(postId: Long): List<CommentEntity>

    fun findByPostIdIn(postIds: List<Long>): List<CommentEntity>

    fun deleteByPostId(postId: Long)
}
