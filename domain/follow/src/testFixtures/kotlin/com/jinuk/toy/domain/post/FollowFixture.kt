package com.jinuk.toy.domain.post

import org.springframework.stereotype.Component
import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.jpa.FollowRepository
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
        id: Long? = null,
        followerUserId: Long = faker.randomLong(),
        followingUserId: Long = faker.randomLong(),
    ) = followRepository.save(create(followerUserId, followingUserId))
}
