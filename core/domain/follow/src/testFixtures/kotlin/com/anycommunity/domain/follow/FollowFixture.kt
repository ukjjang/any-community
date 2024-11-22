package com.anycommunity.domain.follow

import org.springframework.stereotype.Component
import com.anycommunity.domain.follow.jpa.FollowRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

@Component
class FollowFixture(
    private val followRepository: FollowRepository,
) {
    companion object {
        fun create(followerUserId: Long = faker.randomLong(), followingUserId: Long = faker.randomLong()) = Follow(
            followerUserId = followerUserId,
            followingUserId = followingUserId,
        )
    }

    fun persist(followerUserId: Long = faker.randomLong(), followingUserId: Long = faker.randomLong()) =
        followRepository.save(
            create(
                followerUserId,
                followingUserId,
            ),
        )
}
