package com.jinuk.toy.domain.follow

import org.springframework.stereotype.Component
import com.jinuk.toy.domain.follow.jpa.FollowRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong

@Component
class FollowFixture(
    private val followRepository: FollowRepository,
) {
    companion object {
        fun create(
            followerUserId: Long = faker.randomLong(),
            followingUserId: Long = faker.randomLong(),
        ) = Follow(
            followerUserId = followerUserId,
            followingUserId = followingUserId,
        )
    }

    fun persist(
        followerUserId: Long = faker.randomLong(),
        followingUserId: Long = faker.randomLong(),
    ) = followRepository.save(create(followerUserId, followingUserId))
}
