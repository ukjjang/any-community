package com.jinuk.toy.applicaiton.comment.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.util.faker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CreateCommentUsecaseTest(
    private val createCommentUsecase: CreateCommentUsecase,
    private val commentRepository: CommentRepository,
    private val postFixture: PostFixture,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
        {
            describe("댓글 생성 유스케이스") {
                it("생성 성공") {
                    val post = postFixture.persist()
                    val userId = faker.random().nextLong()
                    val command = CreateCommentCommand(userId, post.id, null, "content")
                    createCommentUsecase(command)

                    val comments = commentRepository.findByUserIdAndPostId(userId, post.id)
                    comments.size shouldBe 1
                    comments[0].userId shouldBe command.userId
                    comments[0].postId shouldBe command.postId
                    comments[0].parentCommentId shouldBe command.parentCommentId
                    comments[0].content shouldBe command.content

                    post.commentCount + 1 shouldBe postRepository.findById(post.id)?.commentCount

                    createCommentUsecase(command)
                    createCommentUsecase(command)
                    createCommentUsecase(command)
                    createCommentUsecase(command)

                    post.commentCount + 5 shouldBe postRepository.findById(post.id)?.commentCount
                }
            }
        },
    )
