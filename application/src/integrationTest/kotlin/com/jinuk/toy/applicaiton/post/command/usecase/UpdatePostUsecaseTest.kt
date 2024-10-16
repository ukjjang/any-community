package com.jinuk.toy.applicaiton.post.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe

internal class UpdatePostUsecaseTest(
    private val updatePostUsecase: UpdatePostUsecase,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        extensions(SpringTestExtension(SpringTestLifecycleMode.Test))

        describe("게시글 수정 유스케이스") {
            context("특정 유저가 작성한 게시글이 2개 존재") {
                val exits1 = postFixture.persist()
                val exits2 = postFixture.persist()

                val randomContent = faker.randomString()

                it("수정 성공") {
                    val command = UpdatePostCommand(
                        exits1.userId,
                        exits1.id,
                        PostTitle("faker.randomString()"),
                        faker.randomString()
                    )

                    val post = updatePostUsecase(command)
                    val postEntity = postRepository.findById(post.id)

                    post shouldBe postEntity
                    post.title shouldBe command.title
                    post.content shouldBe command.content
                }

                it("수정 실패 - 작성자가 아닌 유저") {
                    val command = UpdatePostCommand(
                        faker.randomLong(),
                        exits1.id,
                        PostTitle("faker.randomString()"),
                        randomContent,
                    )
                    shouldThrow<IllegalArgumentException> {
                        updatePostUsecase(command)
                    }
                }

                it("수정 실패 - 이미 존재하는 제목") {
                    val command = UpdatePostCommand(
                        exits1.userId,
                        exits1.id,
                        exits2.title,
                        randomContent,
                    )
                    shouldThrow<IllegalArgumentException> {
                        updatePostUsecase(command)
                    }
                }

                it("수정 실패 - 존재하지 않는 포스터 id") {
                    val command = UpdatePostCommand(
                        exits1.userId,
                        faker.randomLong(),
                        PostTitle("faker.randomString()"),
                        randomContent,
                    )
                    shouldThrow<NoSuchElementException> {
                        updatePostUsecase(command)
                    }
                }
            }
        }
    },
)
