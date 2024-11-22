package com.anycommunity.usecase.post.query.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.service.PostQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.util.custompage.CustomPage
import com.anycommunity.util.custompage.toCustomPage

@Service
class SearchPostUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: SearchPostQuery) = cached(
        key = "SearchPostUsecase:${query.hashCode()}",
    ) {
        postQueryService.search(
            keyword = query.keyword,
            pageable = query.pageable(),
            sortType = query.postSearchSortType,
        ).let {
            toCustomPage(it)
        }
    }

    private fun toCustomPage(pages: PageImpl<Post>): CustomPage<SearchedPostResult> {
        val posts = pages.content
        val userIds = posts.map { it.userId }
        val userMap = userQueryService.findByIdIn(userIds).associateBy { it.id }

        val content = posts.map {
            SearchedPostResult(
                id = it.id,
                title = it.title,
                category = it.category,
                userName = userMap.getValue(it.userId).username,
                commentCount = it.commentCount,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }

        return pages.toCustomPage(content)
    }
}

data class SearchPostQuery(
    val keyword: String?,
    val page: Int,
    val size: Int,
    val postSearchSortType: PostSearchSortType,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class SearchedPostResult(
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
