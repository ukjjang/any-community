package com.anycommunity.infra.mysql.post.jdsl

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostSearchCategory
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.infra.mysql.post.entity.PostEntity

@Repository
class PostJdslRepository(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun search(
        keyword: String?,
        pageable: Pageable,
        postSearchCategory: PostSearchCategory,
        sortType: PostSearchSortType,
    ): PageImpl<PostEntity> {
        val totalCount = queryFactory
            .selectQuery<Long> {
                select(count(entity(PostEntity::class)))
                from(entity(PostEntity::class))
                where(condition(keyword, postSearchCategory))
            }.singleResult

        val results = queryFactory
            .selectQuery<PostEntity> {
                select(entity(PostEntity::class))
                from(entity(PostEntity::class))
                where(condition(keyword, postSearchCategory))
                orderBy(sort(sortType))
            }.let {
                it.setFirstResult(pageable.offset.toInt())
                it.maxResults = pageable.pageSize
                it.resultList
            }

        return PageImpl(results, pageable, totalCount)
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.sort(sortType: PostSearchSortType) = when (sortType) {
        PostSearchSortType.RECENTLY -> column(PostEntity::id).desc()
        PostSearchSortType.OLDEST -> column(PostEntity::id).asc()
        PostSearchSortType.MOST_LIKED -> column(PostEntity::likeCount).desc()
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.condition(keyword: String?, postSearchCategory: PostSearchCategory) =
        and(
            keyword?.let { column(PostEntity::title).like("$it%") },
            postSearchCategory.let {
                mapToPostCategory(postSearchCategory)?.let { column(PostEntity::category).equal(it) }
            },
        )

    private fun mapToPostCategory(postSearchCategory: PostSearchCategory): PostCategory? = when (postSearchCategory) {
        PostSearchCategory.ALL -> null
        PostSearchCategory.FREEDOM -> PostCategory.FREEDOM
        PostSearchCategory.HUMOR -> PostCategory.HUMOR
        PostSearchCategory.QUESTION -> PostCategory.QUESTION
        PostSearchCategory.NEWS -> PostCategory.NEWS
        PostSearchCategory.SPORTS -> PostCategory.SPORTS
        PostSearchCategory.TRAVEL -> PostCategory.TRAVEL
        PostSearchCategory.FOOD -> PostCategory.FOOD
        PostSearchCategory.ETC -> PostCategory.ETC
    }
}
