package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.IntegrationTest
import com.jinuk.toy.domain.post.repository.PostRepository
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class PostCreateServiceTest(
    private val postCreateService: PostCreateService,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post create") {
            context("get PostCreateCommand") {

                val title = PostTitle("title")
                val command = PostCreateCommand(title)

                it("create success") {
                    val post = postCreateService(command)
                    val postEntity = postRepository.findById(post.id!!)

                    post shouldBe postEntity
                    post.title shouldBe title
                }
            }
        }
    },
)
