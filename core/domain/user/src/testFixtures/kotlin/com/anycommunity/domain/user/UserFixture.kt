package com.anycommunity.domain.user

import org.springframework.stereotype.Component
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomLongPositive
import com.anycommunity.util.faker.randomString

@Component
class UserFixture(
    private val userRepository: UserRepository,
) {
    companion object {
        fun create(
            username: Username = Username(faker.randomString(15)),
            password: String = faker.randomString(40),
            gender: Gender = faker.randomEnum<Gender>(),
            totalPoints: Point = Point(faker.randomLongPositive()),
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
        totalPoints: Point = Point(faker.randomLongPositive()),
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

    fun clear() {
        userRepository.deleteAll()
    }
}
