package com.anycommunity.usecase.point.usecase.query

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.point.PointTransactionFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryQuery
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class GetPointHistoryUsecaseTest(
    private val getPointHistoryUsecase: GetPointHistoryUsecase,
    private val pointTransactionFixture: PointTransactionFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("포인트 히스토리 조회 유스케이스") {
            it("포인트 히스토리 조회 성공") {
                val userId = faker.randomLong()
                val page = 2
                val size = 20

                val transactions = (1..50).map { pointTransactionFixture.persist(userId = userId) }
                val result = getPointHistoryUsecase(GetPointHistoryQuery(userId, page, size))
                result.size shouldBe size

                val offset = (page - 1) * size
                val sortedTransactions = transactions.sortedByDescending { it.id }.drop(offset).take(size)
                result.content.map { it.amount } shouldBe sortedTransactions.map { it.amount }

                // cache test
                pointTransactionFixture.persist(userId = userId)
                val cachedResult = getPointHistoryUsecase(GetPointHistoryQuery(userId, page, size))
                result shouldBe cachedResult
            }
        }
    },
)
