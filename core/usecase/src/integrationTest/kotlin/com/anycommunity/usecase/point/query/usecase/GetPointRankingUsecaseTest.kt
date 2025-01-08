package com.anycommunity.usecase.point.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.port.query.model.GetPointRankingQuery
import com.anycommunity.usecase.point.usecase.query.GetPointRankingUsecase

internal class GetPointRankingUsecaseTest(
    private val getPointRankingUsecase: GetPointRankingUsecase,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        beforeSpec {
            userFixture.clear()
        }

        describe("포인트 랭킹 조회 유스케이스") {
            context("유저 존재") {
                val users = (1..50).map { userFixture.persist() }
                val page = 2
                val size = 20
                it("포인트 랭킹 조회 성공") {
                    val result = getPointRankingUsecase(GetPointRankingQuery(page, size))
                    result.size shouldBe size

                    val offset = (page - 1) * size
                    val sortedUsers = users.sortedByDescending { it.totalPoints }.drop(offset).take(size)
                    result.content.map { it.ranking } shouldBe ((offset + 1L)..(offset + size.toLong())).map { it }
                    result.content.map { it.userId } shouldBe sortedUsers.map { it.id }

                    // cache test
                    userFixture.persist(totalPoints = Point(Long.MAX_VALUE))
                    val cachedResult = getPointRankingUsecase(GetPointRankingQuery(page, size))
                    result shouldBe cachedResult
                }
            }
        }
    },
)
