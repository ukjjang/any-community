package com.anycommunity.usecase.post.usecase.command

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.post.port.command.model.UpdatePostCommand
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

internal class UpdatePostUsecaseTest(
    private val updatePostUsecase: UpdatePostUsecase,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 수정 유스케이스") {
            it("수정 성공") {
                val exits1 = postFixture.persist()
                val command = UpdatePostCommand(
                    exits1.userId,
                    exits1.id,
                    exits1.title,
                    PostCategory.NEWS,
                    faker.randomString(),
                )

                val result = updatePostUsecase(command)
                result.title shouldBe command.title
                result.content shouldBe command.content
                result.category shouldBe PostCategory.NEWS
            }

            it("수정 실패 - 작성자가 아닌 유저") {
                val exits1 = postFixture.persist()
                val randomContent = faker.randomString()

                val command = UpdatePostCommand(
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
