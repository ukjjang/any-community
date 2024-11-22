package com.anycommunity.usecase.auth.query.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserCredentials
import com.anycommunity.domain.user.service.UserAuthService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomString

internal class LoginUsecaseTest(
    private val loginUsecase: LoginUsecase,
    private val authService: UserAuthService,
) : IntegrationTest, DescribeSpec(
    {
        describe("로그인 유스케이스") {
            context("유저 존재") {
                val signupCredentials = UserCredentials(
                    Username(faker.randomString(4)),
                    RawPassword("Password1"),
                )
                authService.signUp(signupCredentials, gender = Gender.MALE)

                it("로그인 성공") {
                    val query = LoginQuery(signupCredentials.username, signupCredentials.password)
                    loginUsecase(query) shouldNotBe null
                }

                it("로그인 실패 - 잘못된 사용자 이름") {
                    val query =
                        LoginQuery(
                            Username(faker.randomString(5)),
                            signupCredentials.password,
                        )
                    shouldThrow<NoSuchElementException> {
                        loginUsecase(query)
                    }
                }

                it("로그인 실패 - 잘못된 비밀번호") {
                    val query = LoginQuery(
                        signupCredentials.username,
                        RawPassword("WrongPassword1"),
                    )
                    shouldThrow<IllegalArgumentException> {
                        loginUsecase(query)
                    }
                }
            }
        }
    },
)
