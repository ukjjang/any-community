package com.anycommunity.usecase.auth.usecase.command

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.auth.port.command.model.SignupCommand
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomString

internal class SignupUsecaseTest(
    private val signupUsecase: SignupUsecase,
    private val userFixture: UserFixture,
    private val userRepository: UserRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("회원가입 유스케이스") {
            context("유저 존재") {
                val exits = userFixture.persist()
                val password = RawPassword("Password1234")

                it("회원가입 성공") {
                    val command =
                        SignupCommand(
                            Username(faker.randomString(4)),
                            password,
                            Gender.FEMALE,
                        )

                    signupUsecase(command)
                    userRepository.findByUsername(command.username) shouldNotBe null
                }

                it("회원가입 실패 - 동일한 사용자 이름") {
                    val command = SignupCommand(exits.username, password, Gender.MALE)
                    shouldThrow<IllegalArgumentException> {
                        signupUsecase(command)
                    }
                }
            }
        }
    },
)
