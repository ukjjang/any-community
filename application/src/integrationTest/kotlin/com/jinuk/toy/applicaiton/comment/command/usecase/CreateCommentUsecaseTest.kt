package com.jinuk.toy.applicaiton.comment.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.util.faker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CreateCommentUsecaseTest(
    private val createCommentUsecase: CreateCommentUsecase,
    private val commentRepository: CommentRepository,
) : IntegrationTest, DescribeSpec(
        {
            describe("댓글 생성 유스케이스") {
                it("생성 성공") {
                    val userId = faker.random().nextLong()
                    val postId = faker.random().nextLong()
                    val command = CreateCommentCommand(userId, postId, null, "content")
                    createCommentUsecase(command)

                    val searched = commentRepository.findByUserIdAndPostId(userId, postId)
                    searched.size shouldBe 1
                    searched[0].userId shouldBe command.userId
                    searched[0].postId shouldBe command.postId
                    searched[0].parentCommentId shouldBe command.parentCommentId
                    searched[0].content shouldBe command.content
                }
            }
        },
    )
