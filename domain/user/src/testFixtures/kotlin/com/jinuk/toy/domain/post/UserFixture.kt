package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomString
import org.springframework.stereotype.Component

@Component
class UserFixture(
    private val userRepository: UserRepository,
) {
    companion object {
        fun create(
            id: Long? = null,
            username: Username = Username(faker.randomString(15)),
            password: String = faker.randomString(40)
        ) = User(
            _id = id,
            username = username,
            password = password,
        )
    }

    fun persist(
        id: Long? = null,
        username: Username = Username(faker.randomString(15)),
        password: String = faker.randomString(40)
    ) = userRepository.save(create(id, username, password))
}
