package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.PostFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postFixture: PostFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 단일 조회 유스케이스") {
            context("게시글 존재") {
                val exist = postFixture.persist()

                it("조회 성공") {
                    val post = getPostDetailUsecase(GetPostDetailQuery(exist.id))
                    post shouldBe exist
                }

                it("조회 실패 - 다른 id로 조회") {
                    shouldThrow<NoSuchElementException> {
                        getPostDetailUsecase(GetPostDetailQuery(exist.id + 1))
                    }
                }
            }
        }
    },
)

