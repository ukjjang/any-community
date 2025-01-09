package com.anycommunity.usecase.comment.usecase.command

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.comment.port.command.model.CreateCommentCommand
import com.anycommunity.usecase.point.usecase.command.internal.PointProcessUsecase
import com.anycommunity.usecase.post.usecase.command.internal.UpdatePostCommentCountUseCase
import com.anycommunity.util.faker.faker

internal class CreateCommentUsecaseTest(
    private val commentCommandService: CommentCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val commentRepository: CommentRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        val updatePostCommentCountUseCase: UpdatePostCommentCountUseCase = mockk(relaxed = true)
        val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
        val createCommentUsecase =
            CreateCommentUsecase(
                commentCommandService,
                pointRuleQueryService,
                pointProcessUsecase,
                updatePostCommentCountUseCase,
            )

        describe("댓글 생성 유스케이스") {
            beforeTest {
                clearMocks(pointProcessUsecase, updatePostCommentCountUseCase)
            }

            context("게시글 및 유저 존재") {
                val post = postFixture.persist()

                it("생성 성공") {
                    val userId = faker.random().nextLong()
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
                                command.point shouldBe Point(10)
                                command.description.shouldNotBeBlank()
                            },
                        )
                    }

                    verify(exactly = 1) {
                        updatePostCommentCountUseCase(
                            withArg { command ->
                                command.postId shouldBe post.id
                                command.countOperation shouldBe CountOperation.INCREASE
                            },
                        )
                    }
                }

                it("동일 유저가 10분 이내 여러 댓글 생성시 포인트 1회만 지급") {
                    val userId = faker.random().nextLong()
                    val command = CreateCommentCommand(userId, post.id, null, "content")
                    createCommentUsecase(command)
                    createCommentUsecase(command)

                    val comments = commentRepository.findByUserIdAndPostId(userId, post.id)
                    comments.size shouldBe 2

                    verify(exactly = 1) {
                        pointProcessUsecase(
                            withArg { command ->
                                command.userId shouldBe userId
                                command.point shouldBe Point(10)
                                command.description.shouldNotBeBlank()
                            },
                        )
                    }

                    verify(exactly = 2) {
                        updatePostCommentCountUseCase(
                            withArg { command ->
                                command.postId shouldBe post.id
                                command.countOperation shouldBe CountOperation.INCREASE
                            },
                        )
                    }
                }
            }
        }
    },
)
