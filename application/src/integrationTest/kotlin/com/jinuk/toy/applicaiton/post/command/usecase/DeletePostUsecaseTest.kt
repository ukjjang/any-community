package com.jinuk.toy.applicaiton.post.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe

internal class DeletePostUsecaseTest(
    private val deletePostUsecase: DeletePostUsecase,
    private val postRepository: PostRepository,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        extensions(SpringTestExtension(SpringTestLifecycleMode.Test))

        describe("게시글 삭제 유스케이스") {
            context("게시글 존재") {
                val exits = postFixture.persist()

                it("삭제 성공") {
                    val command = DeletePostCommand(exits.userId, exits.id)

                    deletePostUsecase(command)
                    val postEntity = postRepository.findById(exits.id)
                    postEntity shouldBe null
                }

                it("삭제 실패 - 작성자가 아닌 유저") {
                    val command = DeletePostCommand(faker.randomLong(), exits.id)
                    shouldThrow<IllegalArgumentException> {
                        deletePostUsecase(command)
                    }
                }
            }
        }
    },
)
