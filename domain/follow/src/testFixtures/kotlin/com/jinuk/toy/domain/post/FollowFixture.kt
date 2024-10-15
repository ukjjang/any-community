package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong

object FollowFixture {
    fun create(
        id: Long? = null,
        followerUserId: Long = faker.randomLong(),
        followingUserId: Long = faker.randomLong(),
    ): Follow {
        return Follow(
            _id = id,
            followerUserId = followerUserId,
            followingUserId = followingUserId,
        )
    }
}
