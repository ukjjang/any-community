package com.jinuk.toy.externalapi.global.security

import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.util.jwt.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class UserAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userQueryService: UserQueryService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authUser = request.getHeader("Authorization")?.let {
            jwtTokenProvider.resolveToken(it)
        }?.let {
            userQueryService.findByUsername(Username(it))
        }?.let { AuthUser(it) }

        if (authUser != null) {
            val token = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
            token.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = token
        }

        return filterChain.doFilter(request, response)
    }
}
