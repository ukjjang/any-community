package com.jinuk.toy.applicaiton.post.query.usecase

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.applicaiton.post.query.result.SearchedPostResult
import com.jinuk.toy.constant.post.PostSearchSortType
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.redis.cache.cached
import com.jinuk.toy.util.custompage.CustomPage

@Service
class SearchPostUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: SearchPostQuery) =
        cached(
            key = "SearchPostUsecase.invoke.${query.hashCode()}",
        ) {
            val postsPage =
                if (query.keyword != null) {
                    postQueryService.findByTitleStartsWithIgnoreCase(query.keyword, query.pageable())
                } else {
                    postQueryService.findBy(query.pageable())
                }
            createPage(postsPage.content, query.pageable(), postsPage.totalElements)
        }

    private fun createPage(
        posts: List<Post>,
        pageable: Pageable,
        totalSize: Long,
    ): CustomPage<SearchedPostResult> {
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

        return CustomPage(content, pageable, totalSize)
    }
}

data class SearchPostQuery(
    val keyword: String?,
    val page: Int,
    val size: Int,
    val postSearchSortType: PostSearchSortType,
) {
    private fun sort(): Sort =
        when (postSearchSortType) {
            PostSearchSortType.RECENTLY -> Sort.by(Sort.Order.desc("id"))
            PostSearchSortType.OLDEST -> Sort.by(Sort.Order.asc("id"))
        }

    fun pageable(): Pageable = PageRequest.of(page - 1, size, sort())
}
