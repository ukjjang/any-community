package com.jinuk.toy.applicaiton.like.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.like.LikeFixture
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomEnum
import com.jinuk.toy.util.faker.randomLong

internal class CancelLikeUsecaseTest(
    private val cancelLikeUseCase: CancelLikeUsecase,
    private val likeFixture: LikeFixture,
    private val likeQueryService: LikeQueryService,
) : IntegrationTest, DescribeSpec(
        {
            describe("좋아요 삭제 유스케이스") {
                val userId = faker.randomLong()
                val likeTarget = LikeTarget(faker.randomEnum<LikeType>(), faker.randomLong().toString())

                it("좋아요 삭제 성공") {
                    likeFixture.persist(userId = userId, targetType = likeTarget.type, targetId = likeTarget.id)

                    val command = CancelLikeCommand(userId, likeTarget)
                    cancelLikeUseCase(command)

                    likeQueryService.existsByUserIdAndTarget(userId, likeTarget) shouldBe false
                }

                it("좋아요 삭제 실패 - 좋아요 상태가 아님") {
                    val command = CancelLikeCommand(userId, likeTarget)
                    shouldThrow<NoSuchElementException> {
                        cancelLikeUseCase(command)
                    }
                }
            }
        },
    )
