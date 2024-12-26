package com.anycommunity.usecase.post.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.point.service.PointRuleQueryService
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.post.event.PostCreatedEvent
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

internal class CreatePostUsecaseTest(
    private val postCommandService: PostCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 생성 유스케이스") {
            val outboxCreator: OutboxCreator = mockk(relaxed = true)
            val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
            val createPostUsecase =
                CreatePostUsecase(postCommandService, pointRuleQueryService, pointProcessUsecase, outboxCreator)

            beforeTest {
                clearMocks(pointProcessUsecase, outboxCreator)
            }

            context("게시글 존재") {
                val exits = postFixture.persist()

                fun createPostCommand(title: PostTitle = PostTitle(faker.randomString())) =
                    CreatePostCommand(faker.randomLong(), title, PostCategory.ETC, "content")

                it("다른 게시글 제목으로 생성 성공하고, 포인트 지급 유스케이스 호출") {
                    val command = createPostCommand()

                    val result = createPostUsecase(command)
                    result.title shouldBe command.title
                    result.category shouldBe PostCategory.ETC
                    result.content shouldBe "content"

                    verify(exactly = 1) {
                        pointProcessUsecase(
                            withArg { command ->
                                command.point shouldBe Point(50)
                                command.description.shouldNotBeBlank()
                            },
                        )
                    }

                    verify(exactly = 1) {
                        outboxCreator.create(any(), any<PostCreatedEvent>())
                    }
                }

                it("생성에 실패하고 IllegalArgumentException 에러를 던진다.") {
                    val command = createPostCommand(exits.title)
                    shouldThrow<IllegalArgumentException> {
                        createPostUsecase(command)
                    }

                    verify(exactly = 0) {
                        pointProcessUsecase(any())
                    }

                    verify(exactly = 0) {
                        outboxCreator.create(any(), any<PostCreatedEvent>())
                    }
                }
            }
        }
    },
)
