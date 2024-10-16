package com.jinuk.toy.applicaiton.auth.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.UserFixture
import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.domain.user.value.Username
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

internal class SignupUsecaseTest(
    private val signupUsecase: SignupUsecase,
    private val userFixture: UserFixture,
    private val userRepository: UserRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("회원가입 유스케이스") {
            context("유저 존재") {
                val exits = userFixture.persist()

                it("회원가입 성공") {
                    val command = SignupCommand(Username("username"), "password")

                    signupUsecase(command)
                    userRepository.findByUsername(command.username) shouldNotBe null
                }

                it("회원가입 실패 - 동일한 사용자 이름") {
                    val command = SignupCommand(exits.username, "password")
                    shouldThrow<IllegalArgumentException> {
                        signupUsecase(command)
                    }
                }
            }
        }
    },
)
