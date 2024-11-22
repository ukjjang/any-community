package com.anycommunity.domain.post

import org.springframework.stereotype.Component
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.jpa.PostRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

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
