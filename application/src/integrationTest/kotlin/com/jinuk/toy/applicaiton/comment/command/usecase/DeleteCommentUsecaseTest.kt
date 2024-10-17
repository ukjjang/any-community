package com.jinuk.toy.applicaiton.comment.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class DeleteCommentUsecaseTest(
    private val deleteCommentUsecase: DeleteCommentUsecase,
    private val commentRepository: CommentRepository,
    private val commentFixture: CommentFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("댓글 삭제 유스케이스") {
            it("삭제 성공") {
                val exits = commentFixture.persist()
                val command = DeleteCommentCommand(exits.userId, exits.postId, exits.id)

                deleteCommentUsecase(command)
                val comment = commentRepository.findById(exits.id)
                comment shouldBe null
            }

            it("삭제 실패 - 작성자가 아닌 유저") {
                val exits = commentFixture.persist()
                val command = DeleteCommentCommand(faker.randomLong(), exits.postId, exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }
            }

            it("삭제 실패 - 게시글에 속하지 않은 댓글") {
                val exits = commentFixture.persist()
                val command = DeleteCommentCommand(exits.userId, faker.randomLong(), exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }
            }
        }
    },
)
