package com.anycommunity.usecase.comment.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.domain.comment.event.CommentCreatedEvent
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase
import com.anycommunity.util.faker.faker

internal class CreateCommentUsecaseTest(
    private val commentCommandService: CommentCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val commentRepository: CommentRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        val outboxCreator: OutboxCreator = mockk(relaxed = true)
        val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
        val createCommentUsecase =
            CreateCommentUsecase(
                commentCommandService,
                pointRuleQueryService,
                pointProcessUsecase,
                outboxCreator,
            )

        describe("댓글 생성 유스케이스") {
            beforeTest {
                clearMocks(pointProcessUsecase, outboxCreator)
            }

            context("게시글 및 유저 존재") {
                val post = postFixture.persist()
                val userId = faker.random().nextLong()

                it("생성 성공") {
                    val command = CreateCommentCommand(userId, post.id, null, "content")
                    createCommentUsecase(command)

                    val comments = commentRepository.findByUserIdAndPostId(userId, post.id)
                    comments.size shouldBe 1
                    comments[0].userId shouldBe command.userId
                    comments[0].postId shouldBe command.postId
                    comments[0].parentCommentId shouldBe command.parentCommentId
                    comments[0].content shouldBe command.content

                    verify(exactly = 1) {
                        pointProcessUsecase(
                            withArg { command ->
                                command.userId shouldBe userId
                                command.point shouldBe com.anycommunity.definition.point.Point(10)
                                command.description.shouldNotBeBlank()
                            },
                        )
                    }

                    verify(exactly = 1) {
                        outboxCreator.create(any(), any<CommentCreatedEvent>())
                    }
                }
            }
        }
    },
)
