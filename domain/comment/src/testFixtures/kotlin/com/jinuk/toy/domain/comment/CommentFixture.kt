package com.jinuk.toy.domain.comment

import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString
import org.springframework.stereotype.Component

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
        ) = Comment(
            userId = userId,
            postId = postId,
            parentCommentId = parentCommentId,
            content = content,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        postId: Long = faker.randomLong(),
        parentCommentId: Long? = faker.randomLong(),
        content: String = faker.randomString(),
    ) = commentRepository.save(create(userId, postId, parentCommentId, content))
}

