package com.jinuk.toy.domain.user.service

import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.jpa.UserRepository
import com.jinuk.toy.util.jbcrypt.Jbcrypt
import com.jinuk.toy.util.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
) {

    fun signUp(userCredentials: UserCredentials): User {
        require(!userRepository.existsByUsername(userCredentials.username)) { "이미 존재하는 사용자 이름입니다." }
        return User.signup(userCredentials).let { userRepository.save(it) }
    }

    fun login(userCredentials: UserCredentials): String {
        val user =
            userRepository.findByUsername(userCredentials.username) ?: throw NoSuchElementException("존재하지 않는 사용자입니다.")

        require(Jbcrypt.verify(userCredentials.password, user.password)) { "잘못된 비밀번호입니다." }

        return jwtTokenProvider.createToken(user.username.value)
    }
}
