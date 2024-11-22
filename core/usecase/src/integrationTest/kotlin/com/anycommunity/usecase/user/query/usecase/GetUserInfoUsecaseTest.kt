package com.anycommunity.usecase.user.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest

internal class GetUserInfoUsecaseTest(
    private val getUserInfoUsecase: GetUserInfoUsecase,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("회원 정보 조회 유스케이스") {
            context("유저 존재") {
                val user = userFixture.persist()
                it("회원 정보 조회 성공") {
                    val result = getUserInfoUsecase(GetUserInfoQuery(user.username))

                    result.id shouldBe user.id
                    result.username shouldBe user.username
                    result.totalPoints shouldBe user.totalPoints
                }
            }
        }
    },
)
