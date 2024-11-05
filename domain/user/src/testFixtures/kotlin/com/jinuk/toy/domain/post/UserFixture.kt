package com.jinuk.toy.domain.post

import org.springframework.stereotype.Component
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString

@Component
class UserFixture(
    private val userRepository: UserRepository,
) {
    companion object {
        fun create(
            username: Username = Username(faker.randomString(15)),
            password: String = faker.randomString(40),
            followingCount: Long = faker.randomLong(),
            followerCount: Long = faker.randomLong(),
        ) = User(
            username = username,
            password = password,
            followingCount = followingCount,
            followerCount = followerCount,
        )
    }

    fun persist(
        id: Long? = null,
        username: Username = Username(faker.randomString(15)),
        password: String = faker.randomString(40),
        followingCount: Long = faker.randomLong(),
        followerCount: Long = faker.randomLong(),
    ) = userRepository.save(create(username, password, followingCount, followerCount))
}
