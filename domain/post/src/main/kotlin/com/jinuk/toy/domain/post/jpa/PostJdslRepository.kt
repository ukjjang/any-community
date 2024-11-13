package com.jinuk.toy.domain.post.jpa

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.jinuk.toy.common.value.post.PostSearchSortType
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.toModel
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

@Repository
class PostJdslRepository(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun search(
        keyword: String?,
        pageable: Pageable,
        sortType: PostSearchSortType,
    ): PageImpl<Post> {
        val totalCount =
            queryFactory
                .selectQuery<Long> {
                    select(count(entity(PostEntity::class)))
                    from(entity(PostEntity::class))
                    where(condition(keyword))
                }.singleResult

        val results =
            queryFactory
                .selectQuery<PostEntity> {
                    select(entity(PostEntity::class))
                    from(entity(PostEntity::class))
                    where(condition(keyword))
                    orderBy(sort(sortType))
                }.let {
                    it.setFirstResult(pageable.offset.toInt())
                    it.maxResults = pageable.pageSize
                    it.resultList
                }.map { it.toModel() }

        return PageImpl(results, pageable, totalCount)
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.sort(sortType: PostSearchSortType) =
        when (sortType) {
            PostSearchSortType.RECENTLY -> column(PostEntity::id).desc()
            PostSearchSortType.OLDEST -> column(PostEntity::id).asc()
            PostSearchSortType.MOST_LIKED -> column(PostEntity::likeCount).desc()
        }

    private fun <T> SpringDataCriteriaQueryDsl<T>.condition(keyword: String?) =
        keyword?.let {
            column(PostEntity::title).like("$it%")
        }
}
