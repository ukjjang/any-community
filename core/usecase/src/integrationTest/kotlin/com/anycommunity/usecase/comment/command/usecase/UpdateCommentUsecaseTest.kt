package com.anycommunity.usecase.comment.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.comment.CommentFixture
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

internal class UpdateCommentUsecaseTest(
    private val updateCommentUsecase: UpdateCommentUsecase,
    private val commentRepository: CommentRepository,
    private val commentFixture: CommentFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("댓글 수정 유스케이스") {
            it("수정 성공") {
                val exits = commentFixture.persist()
                val command =
                    UpdateCommentCommand(
                        id = exits.id,
                        userId = exits.userId,
                        postId = exits.postId,
                        content = faker.randomString(),
                    )

                updateCommentUsecase(command)
                val comment = commentRepository.findById(command.id)
                comment?.content shouldBe command.content
            }

            it("수정 실패 - 작성자가 아닌 유저") {
                val exits = commentFixture.persist()
                val command =
                    UpdateCommentCommand(
                        id = exits.id,
                        userId = faker.randomLong(),
                        postId = exits.postId,
                        content = faker.randomString(),
                    )
                shouldThrow<IllegalArgumentException> {
                    updateCommentUsecase(command)
                }
            }

            it("수정 실패 - 게시글에 속하지 않은 댓글") {
                val exits = commentFixture.persist()
                val command =
                    UpdateCommentCommand(
                        id = exits.id,
                        userId = exits.userId,
                        postId = faker.randomLong(),
                        content = faker.randomString(),
                    )
                shouldThrow<IllegalArgumentException> {
                    updateCommentUsecase(command)
                }
            }
        }
    },
)
