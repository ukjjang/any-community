package com.anycommunity.usecase.comment.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.comment.CommentFixture
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class DeleteCommentUsecaseTest(
    private val deleteCommentUsecase: DeleteCommentUsecase,
    private val commentRepository: CommentRepository,
    private val commentFixture: CommentFixture,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("댓글 삭제 유스케이스") {
            it("삭제 성공") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(exits.userId, exits.postId, exits.id)

                deleteCommentUsecase(command)
                val comment = commentRepository.findById(exits.id)
                comment shouldBe null
            }

            it("삭제 실패 - 작성자가 아닌 유저") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(faker.randomLong(), exits.postId, exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }
            }

            it("삭제 실패 - 게시글에 속하지 않은 댓글") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(exits.userId, faker.randomLong(), exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }
            }
        }
    },
)