package com.jinuk.toy.applicaiton.comment.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.mockk
import io.mockk.verify
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.applicaiton.point.command.usecase.PointProcessUsecase
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.comment.jpa.CommentRepository
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.point.service.PointRuleQueryService
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.infra.kafka.service.KafkaProducer

internal class CreateCommentUsecaseTest(
    private val commentCommandService: CommentCommandService,
    private val kafkaProducer: KafkaProducer,
    private val pointRuleQueryService: PointRuleQueryService,
    private val commentRepository: CommentRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("댓글 생성 유스케이스") {
            val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
            val createCommentUsecase =
                CreateCommentUsecase(commentCommandService, kafkaProducer, pointRuleQueryService, pointProcessUsecase)

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
                                command.point shouldBe Point(10)
                                command.description.shouldNotBeBlank()
                            },
                        )
                    }
                }
            }
        }
    },
)
