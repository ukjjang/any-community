package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class PostCreateServiceTest(
    private val postCreateService: PostCreateService,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post create") {
            context("post exists title") {
                val existsTitle = PostTitle("exists")
                val exits = PostFixture.create(title = existsTitle)
                postRepository.save(exits)

                it("create success") {
                    val title = PostTitle("title")
                    val command = PostCreateCommand(title)

                    val post = postCreateService.create(command)
                    val postEntity = postRepository.findById(post.id!!)

                    post shouldBe postEntity
                    post.title shouldBe title
                }

                it("create fail - exists title") {
                    val command = PostCreateCommand(existsTitle)
                    shouldThrow<IllegalArgumentException> {
                        postCreateService.create(command)
                    }
                }
            }
        }
    },
)
