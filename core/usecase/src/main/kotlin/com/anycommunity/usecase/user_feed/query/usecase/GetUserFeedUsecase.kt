package com.anycommunity.usecase.user_feed.query.usecase

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.post.service.PostQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.domain.user_feed.UserFeed
import com.anycommunity.domain.user_feed.service.UserFeedQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.util.custompage.CustomPage
import com.anycommunity.util.custompage.toCustomPage

@Service
class GetUserFeedUsecase(
    private val userFeedQueryService: UserFeedQueryService,
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
) {

    @Transactional(readOnly = true)
    operator fun invoke(query: GetUserFeedQuery) = cached(
        key = "GetUserFeedUsecase:${query.hashCode()}",
    ) {
        userFeedQueryService.findByUserIdOrderByIdDesc(
            userId = query.userId,
            pageable = query.pageable(),
        ).let {
            toCustomPage(it)
        }
    }

    private fun toCustomPage(pages: Page<UserFeed>): CustomPage<UserFeedResult> {
        val userFeeds = pages.content

        val postIds = userFeeds.map { it.postId }
        val postMap = postQueryService.findByIdIn(postIds).associateBy { it.id }

        val authorIds = userFeeds.map { it.postAuthorId }
        val authorMap = userQueryService.findByIdIn(authorIds).associateBy { it.id }

        val content = userFeeds.map {
            val post = postMap.getValue(it.postId)
            val author = authorMap.getValue(it.postAuthorId)
            UserFeedResult(
                id = it.id,
                postId = post.id,
                title = post.title,
                category = post.category,
                userName = author.username,
                commentCount = post.commentCount,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }

        return pages.toCustomPage(content)
    }
}

data class GetUserFeedQuery(
    val userId: Long,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class UserFeedResult(
    val id: Long,
    val postId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
