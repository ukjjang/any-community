package com.jinuk.toy.applicaiton.post.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.jpa.PostRepository

internal class UpdatePostCommentCountUseCaseTest(
    private val updatePostCommentCountUseCase: UpdatePostCommentCountUseCase,
    private val postFixture: PostFixture,
    private val postRepository: PostRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 댓글 개수 계산 유스케이스") {
            it("계산") {
                val post = postFixture.persist(commentCount = 0)

                updatePostCommentCountUseCase(UpdatePostCommentCountCommand(post.id, CountOperation.INCREASE))
                postRepository.findById(post.id)?.commentCount shouldBe 1

                updatePostCommentCountUseCase(UpdatePostCommentCountCommand(post.id, CountOperation.DECREMENT))
                postRepository.findById(post.id)?.commentCount shouldBe 0
            }
        }
    },
)
