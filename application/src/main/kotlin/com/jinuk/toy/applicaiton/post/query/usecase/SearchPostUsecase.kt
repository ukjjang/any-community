package com.jinuk.toy.applicaiton.post.query.usecase

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username

@Service
class SearchPostUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: SearchPostQuery): Page<SearchedPostResult> {
        val postsPage =
            if (query.keyword != null) {
                postQueryService.findByTitleStartsWithIgnoreCaseOrderByIdDesc(query.keyword, query.pageable())
            } else {
                postQueryService.findByOrderByIdDesc(query.pageable())
            }
        return createPage(postsPage.content, query.pageable(), postsPage.totalElements)
    }

    private fun createPage(
        posts: List<Post>,
        pageable: Pageable,
        totalSize: Long,
    ): Page<SearchedPostResult> {
        val userIds = posts.map { it.userId }
        val userMap = userQueryService.findByIdIn(userIds).associateBy { it.id }

        val content =
            posts.map {
                SearchedPostResult(
                    id = it.id,
                    title = it.title,
                    userName = userMap.getValue(it.userId).username,
                    commentCount = it.commentCount,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                )
            }

        return PageImpl(content, pageable, totalSize)
    }
}

data class SearchPostQuery(
    val keyword: String?,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class SearchedPostResult(
    val id: Long,
    val title: PostTitle,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
