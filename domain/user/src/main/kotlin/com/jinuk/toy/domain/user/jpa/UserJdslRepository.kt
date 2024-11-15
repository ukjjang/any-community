package com.jinuk.toy.domain.user.jpa

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.selectQuery
import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.user.toModel
import com.jinuk.toy.infra.rdb.user.entity.UserEntity

@Repository
class UserJdslRepository(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun getPointRankingUser(limit: Int) = queryFactory.selectQuery<UserEntity> {
        select(entity(UserEntity::class))
        from(entity(UserEntity::class))
        orderBy(column(UserEntity::totalPoints).desc())
        limit(limit)
    }.resultList.map { it.toModel() }
}
