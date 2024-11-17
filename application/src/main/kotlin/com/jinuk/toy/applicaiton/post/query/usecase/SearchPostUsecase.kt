package com.jinuk.toy.applicaiton.post.query.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.jinuk.toy.common.util.custompage.CustomPage
import com.jinuk.toy.common.util.custompage.toCustomPage
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostSearchSortType
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.redis.cache.cached

@Service
class SearchPostUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: SearchPostQuery) = cached(
        key = "SearchPostUsecase:${query.cacheKey}",
    ) {
        postQueryService
            .search(
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

        val content =
            posts.map {
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
    val cacheKey: String
        get() = "keyword:$keyword:page:$page:size:$size:postSearchSortType:$postSearchSortType"

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
