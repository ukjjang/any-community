package com.jinuk.toy.applicaiton.post.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository

internal class UpdatePostUsecaseTest(
    private val updatePostUsecase: UpdatePostUsecase,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 수정 유스케이스") {
                it("수정 성공") {
                    val exits1 = postFixture.persist()
                    val command =
                        UpdatePostCommand(
                            exits1.userId,
                            exits1.id,
                            PostTitle("faker.randomString()"),
                            PostCategory.NEWS,
                            faker.randomString(),
                        )

                    val post = updatePostUsecase(command)
                    val postEntity = postRepository.findById(post.id)

                    post shouldBe postEntity
                    post.title shouldBe command.title
                    post.content shouldBe command.content
                    post.category shouldBe PostCategory.NEWS
                }

                it("수정 실패 - 작성자가 아닌 유저") {
                    val exits1 = postFixture.persist()
                    val randomContent = faker.randomString()

                    val command =
                        UpdatePostCommand(
                            faker.randomLong(),
                            exits1.id,
                            PostTitle("faker.randomString()"),
                            PostCategory.NEWS,
                            randomContent,
                        )
                    shouldThrow<IllegalArgumentException> {
                        updatePostUsecase(command)
                    }
                }

                it("수정 실패 - 이미 존재하는 제목") {
                    val exits1 = postFixture.persist()
                    val exits2 = postFixture.persist()

                    val randomContent = faker.randomString()

                    val command =
                        UpdatePostCommand(
                            exits1.userId,
                            exits1.id,
                            exits2.title,
                            PostCategory.NEWS,
                            randomContent,
                        )
                    shouldThrow<IllegalArgumentException> {
                        updatePostUsecase(command)
                    }
                }

                it("수정 실패 - 존재하지 않는 포스터 id") {
                    val exits1 = postFixture.persist()
                    val randomContent = faker.randomString()

                    val command =
                        UpdatePostCommand(
                            exits1.userId,
                            faker.randomLong(),
                            PostTitle(faker.randomString()),
                            PostCategory.NEWS,
                            randomContent,
                        )
                    shouldThrow<NoSuchElementException> {
                        updatePostUsecase(command)
                    }
                }
            }
        },
    )
