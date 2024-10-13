package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker

object UserFixture {
    fun create(
        id: Long? = null,
        username: Username = Username(faker.lorem().characters(1, 30)),
        password: String = faker.lorem().characters(1, 30)
    ): User {
        return User(
            id = id,
            username = username,
            password = password,
        )
    }
}
