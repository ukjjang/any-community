package com.jinuk.toy.domain.user

import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.jbcrypt.Jbcrypt
import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val username: Username,
    val password: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    companion object {
        fun signup(userCredentials: UserCredentials) =
            User(username = userCredentials.username, password = Jbcrypt.encrypt(userCredentials.password))
    }
}
