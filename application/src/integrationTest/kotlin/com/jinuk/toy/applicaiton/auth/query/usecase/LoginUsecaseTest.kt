package com.jinuk.toy.applicaiton.auth.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomString
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

internal class LoginUsecaseTest(
    private val loginUsecase: LoginUsecase,
    private val authService: UserAuthService,
) : IntegrationTest, DescribeSpec(
    {
        describe("로그인 유스케이스") {
            context("유저 존재") {
                val signupCredentials = UserCredentials(Username("username"), "password")
                authService.signUp(signupCredentials)

                it("로그인 성공") {
                    val query = LoginQuery(signupCredentials.username, signupCredentials.password)
                    loginUsecase(query) shouldNotBe null
                }

                it("로그인 실패 - 잘못된 사용자 이름") {
                    val query = LoginQuery(Username(faker.randomString(5)), signupCredentials.password)
                    shouldThrow<NoSuchElementException> {
                        loginUsecase(query)
                    }
                }

                it("로그인 실패 - 잘못된 비밀번호") {
                    val query = LoginQuery(signupCredentials.username, faker.randomString())
                    shouldThrow<IllegalArgumentException> {
                        loginUsecase(query)
                    }
                }
            }
        }
    },
)
