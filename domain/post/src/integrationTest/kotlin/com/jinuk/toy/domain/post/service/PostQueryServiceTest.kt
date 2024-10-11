package com.jinuk.toy.domain.post.service

import com.jinuk.toy.domain.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PostQueryServiceTest(
    private val postQueryService: PostQueryService,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("post query") {
            context("post exists") {
                val exist =postRepository.save(PostFixture.create())

                it("query success") {
                    val post = postQueryService.getById(exist.id!!)
                    post shouldBe exist
                }

                it("query fail") {
                    shouldThrow<NoSuchElementException> {
                        postQueryService.getById(exist.id!! + 1)
                    }
                }
            }
        }
    },
)

