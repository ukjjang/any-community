package com.anycommunity.usecase.post.usecase.query

import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.service.PostQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.usecase.post.port.query.model.SearchPostQuery
import com.anycommunity.usecase.post.port.query.model.SearchedPostResult
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
            postSearchCategory = query.postSearchCategory,
            sortType = query.postSearchSortType,
        ).let {
            toCustomPage(it)
        }
    }

    private fun toCustomPage(pages: PageImpl<Post>): CustomPage<SearchedPostResult> {
        val posts = pages.content.toList()
        val userIds = posts.map { it.userId }
        val userMap = userQueryService.findByIdIn(userIds).associateBy { it.id }

        val content = posts.map {
            SearchedPostResult(
                id = it.id,
                title = it.title,
                category = it.category,
                userName = userMap.getValue(it.userId).username,
                commentCount = it.commentCount,
                likeCount = it.likeCount,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }

        return pages.toCustomPage(content)
    }
}
