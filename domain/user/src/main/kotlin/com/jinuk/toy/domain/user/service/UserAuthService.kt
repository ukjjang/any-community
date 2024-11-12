package com.jinuk.toy.domain.user.service

import org.springframework.stereotype.Service
import com.jinuk.toy.common.util.jbcrypt.Jbcrypt
import com.jinuk.toy.common.util.jwt.JwtTokenProvider
import com.jinuk.toy.constant.user.Gender
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.jpa.UserRepository

@Service
class UserAuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
) {
    fun signUp(
        userCredentials: UserCredentials,
        gender: Gender,
    ): User {
        require(!userRepository.existsByUsername(userCredentials.username)) { "이미 존재하는 사용자 이름입니다." }
        return User.signup(userCredentials, gender).let { userRepository.save(it) }
    }

    fun login(userCredentials: UserCredentials): String {
        val user =
            userRepository.findByUsername(userCredentials.username) ?: throw NoSuchElementException("존재하지 않는 사용자입니다.")

        require(Jbcrypt.verify(userCredentials.password, user.password)) { "잘못된 비밀번호입니다." }

        return jwtTokenProvider.createToken(user.username.value)
    }
}
