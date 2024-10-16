package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString
import org.springframework.stereotype.Component

@Component
class PostFixture(
    private val postRepository: PostRepository,
) {
    companion object {
        fun create(
            id: Long? = null,
            userId: Long = faker.randomLong(),
            title: PostTitle = PostTitle(faker.randomString(20)),
            content: String = faker.randomString(100),
        ) = Post(
            userId = userId,
            _id = id,
            title = title,
            content = content
        )
    }

    fun persist(
        id: Long? = null,
        userId: Long = faker.randomLong(),
        title: PostTitle = PostTitle(faker.randomString(20)),
        content: String = faker.randomString(100),
    ) = postRepository.save(create(id, userId, title, content))
}
