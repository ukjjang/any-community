package com.anycommunity.domain.user_feed.jpa

import org.springframework.data.domain.Pageable
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

    fun saveAll(userFeeds: List<UserFeed>) =
        userFeedEntityRepository.saveAll(userFeeds.map { it.toEntity() }).map { it.toModel() }

    fun delete(userFeed: UserFeed) = userFeedEntityRepository.delete(userFeed.toEntity())

    fun findById(id: Long) = userFeedEntityRepository.findByIdOrNull(id)?.toModel()
    fun findByUserId(userId: Long) = userFeedEntityRepository.findByUserId(userId)
    fun findByUserIdOrderByIdDesc(userId: Long, pageable: Pageable) =
        userFeedEntityRepository.findByUserIdOrderByIdDesc(userId, pageable).map { it.toModel() }

    fun findByPostId(postId: Long) = userFeedEntityRepository.findByPostId(postId).map { it.toModel() }

    fun deleteAll() = userFeedEntityRepository.deleteAll()

    fun existsByUserIdAndPostId(userId: Long, postId: Long) =
        userFeedEntityRepository.existsByUserIdAndPostId(userId, postId)

    fun deleteByPostId(postId: Long) = userFeedEntityRepository.deleteByPostId(postId)
    fun deleteByUserIdAndPostAuthorId(userId: Long, postAuthorId: Long) =
        userFeedEntityRepository.deleteByUserIdAndPostAuthorId(userId, postAuthorId)
}
