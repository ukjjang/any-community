package com.jinuk.toy.domain.user

import org.springframework.stereotype.Component
import com.jinuk.toy.constant.user.Gender
import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomEnum
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
            gender: Gender = faker.randomEnum<Gender>(),
            totalPoints: Long = faker.randomLong(),
            followingCount: Long = faker.randomLong(),
            followerCount: Long = faker.randomLong(),
        ) = User(
            username = username,
            password = password,
            gender = gender,
            totalPoints = totalPoints,
            followingCount = followingCount,
            followerCount = followerCount,
        )
    }

    fun persist(
        id: Long? = null,
        username: Username = Username(faker.randomString(15)),
        password: String = faker.randomString(40),
        gender: Gender = faker.randomEnum<Gender>(),
        totalPoints: Long = faker.randomLong(),
        followingCount: Long = faker.randomLong(),
        followerCount: Long = faker.randomLong(),
    ) = userRepository.save(
        create(
            username = username,
            password = password,
            gender = gender,
            totalPoints = totalPoints,
            followingCount = followingCount,
            followerCount = followerCount,
        ),
    )
}
