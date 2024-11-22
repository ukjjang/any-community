package com.anycommunity.usecase.point.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest

internal class GetPointRankingUsecaseTest(
    private val getPointRankingUsecase: GetPointRankingUsecase,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        beforeSpec {
            userFixture.clear()
        }

        describe("회원 정보 조회 유스케이스") {
            context("유저 존재") {
                val users = (1..50).map { userFixture.persist() }
                val limit = 20
                it("회원 정보 조회 성공") {
                    val result = getPointRankingUsecase(GetPointRankingQuery(limit))
                    result.size shouldBe limit

                    val sortedUsers = users.sortedByDescending { it.totalPoints }.take(limit)
                    result.map { it.ranking } shouldBe (1..limit).map { it }
                    result.map { it.userId } shouldBe sortedUsers.map { it.id }

                    // cache test
                    userFixture.persist(totalPoints = com.anycommunity.definition.point.Point(Long.MAX_VALUE))
                    val cachedResult = getPointRankingUsecase(GetPointRankingQuery(limit))
                    result shouldBe cachedResult
                }
            }
        }
    },
)
