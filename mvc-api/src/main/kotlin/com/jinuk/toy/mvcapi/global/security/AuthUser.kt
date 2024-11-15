package com.jinuk.toy.mvcapi.global.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.user.User

data class AuthUser(
    val id: Long,
    val username: Username,
    val userRoles: List<String> = listOf(),
) : UserDetails {
    companion object {
        operator fun invoke(user: User) = AuthUser(
            id = user.id,
            username = user.username,
            userRoles = listOf(AuthRole.USER),
        )
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = userRoles.stream()
        .map { SimpleGrantedAuthority(it) }
        .toList()

    override fun getPassword(): String = ""

    override fun getUsername(): String = ""
}

object AuthRole {
    const val USER = "USER"
}
