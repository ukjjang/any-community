package com.anycommunity.domain.user.service

import org.springframework.stereotype.Service
import com.anycommunity.definition.user.Gender
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.UserCredentials
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.util.jbcrypt.Jbcrypt
import com.anycommunity.util.jwt.JwtTokenProvider

@Service
class UserAuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
) {
    fun signUp(userCredentials: UserCredentials, gender: Gender): User {
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
