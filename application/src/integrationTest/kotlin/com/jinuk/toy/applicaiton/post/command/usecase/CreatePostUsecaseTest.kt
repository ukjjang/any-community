package com.jinuk.toy.applicaiton.post.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CreatePostUsecaseTest(
    private val createPostUsecase: CreatePostUsecase,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 생성 유스케이스") {
            context("게시글 존재") {
                val exits = postFixture.persist()

                it("생성 성공") {
                    val title = PostTitle("title")
                    val command = CreatePostCommand(1, title, "content")

                    val post = createPostUsecase(command)
                    val postEntity = postRepository.findById(post.id)

                    post shouldBe postEntity
                    post.userId shouldBe 1
                    post.title shouldBe title
                    post.content shouldBe "content"
                }

                it("생성 실패 - 동일한 제목") {
                    val command = CreatePostCommand(1, exits.title, "content")
                    shouldThrow<IllegalArgumentException> {
                        createPostUsecase(command)
                    }
                }
            }
        }
    },
)
