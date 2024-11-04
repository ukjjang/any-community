package com.jinuk.toy.domain.post

import org.springframework.stereotype.Component
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString

@Component
class PostFixture(
    private val postRepository: PostRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            title: PostTitle = PostTitle(faker.randomString(20)),
            content: String = faker.randomString(100),
            commentCount: Long = faker.randomLong(),
            likeCount: Long = faker.randomLong(),
        ) = Post(
            userId = userId,
            title = title,
            content = content,
            commentCount = commentCount,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        title: PostTitle = PostTitle(faker.randomString(20)),
        content: String = faker.randomString(100),
        commentCount: Long = faker.randomLong(),
        likeCount: Long = faker.randomLong(),
    ) = postRepository.save(
        create(
            userId = userId,
            title = title,
            content = content,
            commentCount = commentCount,
            likeCount = likeCount,
        ),
    )
}
