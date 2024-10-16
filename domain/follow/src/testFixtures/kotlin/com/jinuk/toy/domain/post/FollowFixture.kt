package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.jpa.FollowRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import org.springframework.stereotype.Component

@Component
class FollowFixture(
    private val followRepository: FollowRepository,
) {
    companion object {
        fun create(
            id: Long? = null,
            followerUserId: Long = faker.randomLong(),
            followingUserId: Long = faker.randomLong(),
        ) = Follow(
            _id = id,
            followerUserId = followerUserId,
            followingUserId = followingUserId,
        )
    }

    fun persist(
        id: Long? = null,
        followerUserId: Long = faker.randomLong(),
        followingUserId: Long = faker.randomLong(),
    ) = followRepository.save(create(id, followerUserId, followingUserId))
}

