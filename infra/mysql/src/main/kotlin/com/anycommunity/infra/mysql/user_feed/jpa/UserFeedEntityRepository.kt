package com.anycommunity.infra.mysql.user_feed.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.infra.mysql.user_feed.entity.UserFeedEntity

interface UserFeedEntityRepository : JpaRepository<UserFeedEntity, Long> {
    fun existsByUserIdAndPostId(userId: Long, postId: Long): Boolean
    fun deleteByPostId(postId: Long)
    fun deleteByUserIdAndPostAuthorId(userId: Long, postAuthorId: Long)
    fun findByUserId(userId: Long): List<UserFeedEntity>
    fun findByPostId(postId: Long): List<UserFeedEntity>
}
