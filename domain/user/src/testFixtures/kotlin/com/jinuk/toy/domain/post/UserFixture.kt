package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomString

object UserFixture {
    fun create(
        id: Long? = null,
        username: Username = Username(faker.randomString(15)),
        password: String = faker.randomString(40)
    ): User {
        return User(
            _id = id,
            username = username,
            password = password,
        )
    }
}
