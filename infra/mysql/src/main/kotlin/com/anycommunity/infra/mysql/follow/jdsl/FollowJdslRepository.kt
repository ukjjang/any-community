package com.anycommunity.infra.mysql.follow.jdsl

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.anycommunity.definition.follow.FollowSearchSortType
import com.anycommunity.infra.mysql.follow.entity.FollowEntity

@Repository
class FollowJdslRepository(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun search(
        followingUserId: Long? = null,
        followerUserId: Long? = null,
        pageable: Pageable,
        sortType: FollowSearchSortType,
    ): PageImpl<FollowEntity> {
        val totalCount = queryFactory
            .selectQuery<Long> {
                select(count(entity(FollowEntity::class)))
                from(entity(FollowEntity::class))
                where(condition(followingUserId = followingUserId, followerUserId = followerUserId))
            }.singleResult

        val results = queryFactory
            .selectQuery<FollowEntity> {
                select(entity(FollowEntity::class))
                from(entity(FollowEntity::class))
                where(condition(followingUserId = followingUserId, followerUserId = followerUserId))
                orderBy(sort(sortType))
            }.let {
                it.setFirstResult(pageable.offset.toInt())
                it.maxResults = pageable.pageSize
                it.resultList
            }

        return PageImpl(results, pageable, totalCount)
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.sort(sortType: FollowSearchSortType) = when (sortType) {
        FollowSearchSortType.RECENTLY -> column(FollowEntity::id).desc()
        FollowSearchSortType.OLDEST -> column(FollowEntity::id).asc()
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.condition(followingUserId: Long?, followerUserId: Long?) = and(
        followingUserId?.let { column(FollowEntity::followingUserId).equal(it) },
        followerUserId?.let { column(FollowEntity::followerUserId).equal(it) },
    )
}
