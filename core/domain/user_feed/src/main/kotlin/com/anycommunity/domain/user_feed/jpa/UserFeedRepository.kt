package com.anycommunity.domain.user_feed.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import com.anycommunity.domain.user_feed.UserFeed
import com.anycommunity.domain.user_feed.toEntity
import com.anycommunity.domain.user_feed.toModel
import com.anycommunity.infra.mysql.user_feed.jpa.UserFeedEntityRepository

@Repository
class UserFeedRepository(
    private val userFeedEntityRepository: UserFeedEntityRepository,
) {
    fun save(userFeed: UserFeed) = userFeedEntityRepository.save(userFeed.toEntity()).toModel()

    fun delete(userFeed: UserFeed) = userFeedEntityRepository.delete(userFeed.toEntity())

    fun findById(id: Long) = userFeedEntityRepository.findByIdOrNull(id)?.toModel()

    fun deleteAll() = userFeedEntityRepository.deleteAll()

    fun existsByUserIdAndPostId(userId: Long, postId: Long) =
        userFeedEntityRepository.existsByUserIdAndPostId(userId, postId)

    fun deleteByPostId(postId: Long) = userFeedEntityRepository.deleteByPostId(postId)
}
