package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.util.faker.faker

object PostFixture {
    fun create(
        id: Long? = null,
        userId: Long = faker.random().nextLong(),
        title: PostTitle = PostTitle(faker.lorem().characters(1, 30)),
        content: String = faker.lorem().characters(1, 300)
    ): Post {
        return Post(
            userId = userId,
            id = id,
            title = title,
            content = content
        )
    }
}
