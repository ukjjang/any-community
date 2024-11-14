package com.jinuk.toy.applicaiton.post.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.applicaiton.point.command.PointCommandBus
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.point.service.PointRuleQueryService
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.domain.post.service.PostQueryService

internal class CreatePostUsecaseTest(
    private val postQueryService: PostQueryService,
    private val postCommandService: PostCommandService,
    private val pointRuleQueryService: PointRuleQueryService,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 생성 유스케이스") {
                val pointCommandBus: PointCommandBus = mockk(relaxed = true)
                val createPostUsecase =
                    CreatePostUsecase(postQueryService, postCommandService, pointRuleQueryService, pointCommandBus)

                beforeEach {
                    clearMocks(pointCommandBus)
                }

                context("게시글 존재") {
                    val exits = postFixture.persist()

                    fun createPostCommand(title: PostTitle = PostTitle(faker.randomString())) =
                        CreatePostCommand(faker.randomLong(), title, PostCategory.ETC, "content")

                    it("생성의 성공하고, 포인트 지급 유스케이스 호출") {
                        val command = createPostCommand()

                        val post = createPostUsecase(command)
                        val postEntity = postRepository.findById(post.id)
                        post shouldBe postEntity
                        post.userId shouldBe command.userId
                        post.title shouldBe command.title
                        post.category shouldBe PostCategory.ETC
                        post.content shouldBe "content"

                        verify(exactly = 1) {
                            pointCommandBus.execute(
                                withArg { command ->
                                    command.userId shouldBe post.userId
                                    command.point shouldBe Point(50)
                                    command.description.shouldNotBeBlank()
                                },
                            )
                        }
                    }

                    it("생성에 실패하고 IllegalArgumentException 에러를 던진다.") {
                        val command = createPostCommand(exits.title)
                        shouldThrow<IllegalArgumentException> {
                            createPostUsecase(command)
                        }

                        verify(exactly = 0) {
                            pointCommandBus.execute(any())
                        }
                    }
                }
            }
        },
    )
