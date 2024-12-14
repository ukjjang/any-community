package com.anycommunity.usecase.comment.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.domain.comment.CommentFixture
import com.anycommunity.domain.comment.event.CommentDeletedEvent
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class DeleteCommentUsecaseTest(
    private val commentCommandService: CommentCommandService,
    private val likeCommandService: LikeCommandService,
    private val commentRepository: CommentRepository,
    private val commentFixture: CommentFixture,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        val outboxCreator: OutboxCreator = mockk(relaxed = true)
        val deleteCommentUsecase =
            DeleteCommentUsecase(commentCommandService, likeCommandService, outboxCreator)

        describe("댓글 삭제 유스케이스") {

            beforeTest {
                clearMocks(outboxCreator)
            }

            it("삭제 성공") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(exits.userId, exits.postId, exits.id)

                deleteCommentUsecase(command)
                val comment = commentRepository.findById(exits.id)
                comment shouldBe null

                verify(exactly = 1) {
                    outboxCreator.create(any(), any<CommentDeletedEvent>())
                }
            }

            it("삭제 실패 - 작성자가 아닌 유저") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(faker.randomLong(), exits.postId, exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }

                verify(exactly = 0) {
                    outboxCreator.create(any(), any<CommentDeletedEvent>())
                }
            }

            it("삭제 실패 - 게시글에 속하지 않은 댓글") {
                val post = postFixture.persist()
                val exits = commentFixture.persist(postId = post.id)
                val command = DeleteCommentCommand(exits.userId, faker.randomLong(), exits.id)

                shouldThrow<IllegalArgumentException> {
                    deleteCommentUsecase(command)
                }

                verify(exactly = 0) {
                    outboxCreator.create(any(), any<CommentDeletedEvent>())
                }
            }
        }
    },
)
