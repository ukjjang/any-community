package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.util.faker.faker

object PostFixture {
    fun create(
        id: Long = faker.random().nextLong(),
        title: PostTitle = PostTitle(faker.lorem().characters(1, 30))
    ): Post {
        return Post(
            id = id,
            title = title,
        )
    }
}
