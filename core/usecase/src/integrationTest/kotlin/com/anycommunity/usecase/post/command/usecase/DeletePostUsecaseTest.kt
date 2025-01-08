package com.anycommunity.usecase.post.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.domain.comment.CommentFixture
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.comment.service.CommentQueryService
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.post.event.PostDeletedEvent
import com.anycommunity.domain.post.jpa.PostRepository
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.post.port.command.model.DeletePostCommand
import com.anycommunity.usecase.post.usecase.command.DeletePostUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class DeletePostUsecaseTest(
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
    private val commentFixture: CommentFixture,
    private val commentRepository: CommentRepository,
    private val postCommandService: PostCommandService,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val likeCommandService: LikeCommandService,
) : IntegrationTest, DescribeSpec(
    {
        val outboxCreator: OutboxCreator = mockk(relaxed = true)
        val deletePostUsecase =
            DeletePostUsecase(
                postCommandService,
                commentCommandService,
                commentQueryService,
                likeCommandService,
                outboxCreator,
            )

        describe("게시글 삭제 유스케이스") {
            beforeTest {
                clearMocks(outboxCreator)
            }

            it("삭제 성공") {
                val exits = postFixture.persist()

                commentFixture.persist(postId = exits.id)
                commentFixture.persist(postId = exits.id)
                val anotherPostComment = commentFixture.persist()

                val command = DeletePostCommand(exits.userId, exits.id)
                deletePostUsecase(command)

                postRepository.findById(exits.id) shouldBe null
                commentRepository.findByPostId(command.id) shouldBe emptyList()
                commentRepository.findById(anotherPostComment.id) shouldNotBe null

                verify(exactly = 1) {
                    outboxCreator.create(any(), any<PostDeletedEvent>())
                }
            }

            it("삭제 실패 - 작성자가 아닌 유저") {
                val exits = postFixture.persist()
                val command = DeletePostCommand(faker.randomLong(), exits.id)
                shouldThrow<IllegalArgumentException> {
                    deletePostUsecase(command)
                }

                verify(exactly = 0) {
                    outboxCreator.create(any(), any<PostDeletedEvent>())
                }
            }
        }
    },
)
