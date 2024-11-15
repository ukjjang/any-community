package com.jinuk.toy.applicaiton.auth.query.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.common.value.user.Gender
import com.jinuk.toy.common.value.user.RawPassword
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService

internal class LoginUsecaseTest(
    private val loginUsecase: LoginUsecase,
    private val authService: UserAuthService,
) : IntegrationTest, DescribeSpec(
    {
        describe("로그인 유스케이스") {
            context("유저 존재") {
                val signupCredentials = UserCredentials(Username(faker.randomString(4)), RawPassword("Password1"))
                authService.signUp(signupCredentials, gender = Gender.MALE)

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
                    val query = LoginQuery(signupCredentials.username, RawPassword("WrongPassword1"))
                    shouldThrow<IllegalArgumentException> {
                        loginUsecase(query)
                    }
                }
            }
        }
    },
)
