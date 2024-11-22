package com.anycommunity.mvcapi.global.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.util.jwt.JwtTokenProvider

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
        val authUser =
            request.getHeader("Authorization")?.let {
                jwtTokenProvider.resolveToken(it)
            }?.let {
                userQueryService.findByUsername(Username(it))
            }?.let { AuthUser.from(it) }

        if (authUser != null) {
            val token = UsernamePasswordAuthenticationToken(authUser, null, authUser.authorities)
            token.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = token
        }

        return filterChain.doFilter(request, response)
    }
}
