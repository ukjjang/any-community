package com.anycommunity.usecase.like.usecase.command.internal

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.CommentFixture
import com.anycommunity.domain.comment.jpa.CommentRepository
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.post.jpa.PostRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.like.command.port.command.model.UpdateLikeCountCommand
import com.anycommunity.usecase.like.command.usecase.command.internal.UpdateLikeCountUsecase

class UpdateLikeCountUsecaseTest(
    private val updateLikeCountUsecase: UpdateLikeCountUsecase,
    private val postFixture: PostFixture,
    private val commentFixture: CommentFixture,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("좋아요 개수 업데이트 유스케이스") {
            context("게시글 좋아요 카운트") {
                val post = postFixture.persist(likeCount = 0)
                val likeTarget = LikeTarget.Companion.from(LikeType.POST, post.id)

                updateLikeCountUsecase(UpdateLikeCountCommand(likeTarget, CountOperation.INCREASE))
                postRepository.findById(post.id)?.likeCount shouldBe 1

                updateLikeCountUsecase(UpdateLikeCountCommand(likeTarget, CountOperation.DECREMENT))
                postRepository.findById(post.id)?.likeCount shouldBe 0
            }
            context("댓글 좋아요 카운트") {
                val comment = commentFixture.persist(likeCount = 0)
                val likeTarget = LikeTarget.Companion.from(LikeType.COMMENT, comment.id)

                updateLikeCountUsecase(UpdateLikeCountCommand(likeTarget, CountOperation.INCREASE))
                commentRepository.findById(comment.id)?.likeCount shouldBe 1

                updateLikeCountUsecase(UpdateLikeCountCommand(likeTarget, CountOperation.DECREMENT))
                commentRepository.findById(comment.id)?.likeCount shouldBe 0
            }
        }
    },
)
