package com.anycommunity.domain.comment

import org.springframework.stereotype.Component
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

@Component
class CommentFixture(
    private val commentRepository: CommentRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            postId: Long = faker.randomLong(),
            parentCommentId: Long? = faker.randomLong(),
            content: String = faker.randomString(),
            likeCount: Long = faker.randomLong(),
        ) = Comment(
            userId = userId,
            postId = postId,
            parentCommentId = parentCommentId,
            content = content,
            likeCount = likeCount,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        postId: Long = faker.randomLong(),
        parentCommentId: Long? = faker.randomLong(),
        content: String = faker.randomString(),
        likeCount: Long = faker.randomLong(),
    ) = commentRepository.save(create(userId, postId, parentCommentId, content, likeCount))
}
