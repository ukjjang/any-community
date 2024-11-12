package com.jinuk.toy.domain.post

import org.springframework.stereotype.Component
import com.jinuk.toy.common.define.post.PostCategory
import com.jinuk.toy.common.define.post.PostTitle
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomEnum
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.domain.post.jpa.PostRepository

@Component
class PostFixture(
    private val postRepository: PostRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            title: PostTitle = PostTitle(faker.randomString(20)),
            category: PostCategory = faker.randomEnum<PostCategory>(),
            content: String = faker.randomString(100),
            commentCount: Long = faker.randomLong(),
            likeCount: Long = faker.randomLong(),
        ) = Post(
            userId = userId,
            title = title,
            category = category,
            content = content,
            commentCount = commentCount,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        title: PostTitle = PostTitle(faker.randomString(20)),
        category: PostCategory = faker.randomEnum<PostCategory>(),
        content: String = faker.randomString(100),
        commentCount: Long = faker.randomLong(),
        likeCount: Long = faker.randomLong(),
    ) = postRepository.save(
        create(
            userId = userId,
            title = title,
            category = category,
            content = content,
            commentCount = commentCount,
            likeCount = likeCount,
        ),
    )
}
