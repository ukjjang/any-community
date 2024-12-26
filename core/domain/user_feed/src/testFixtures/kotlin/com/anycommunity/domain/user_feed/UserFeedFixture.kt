package com.anycommunity.domain.user_feed

import org.springframework.stereotype.Component
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

@Component
class UserFeedFixture(
    private val userFeedRepository: UserFeedRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            postId: Long = faker.randomLong(),
            postAuthorId: Long = faker.randomLong(),
        ) = UserFeed(
            userId = userId,
            postId = postId,
            postAuthorId = postAuthorId,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        postId: Long = faker.randomLong(),
        postAuthorId: Long = faker.randomLong(),
    ) = userFeedRepository.save(
        create(
            userId = userId,
            postId = postId,
            postAuthorId = postAuthorId,
        ),
    )

    fun clear() {
        userFeedRepository.deleteAll()
    }
}
