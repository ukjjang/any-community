package com.anycommunity.mvcapi.health

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser

@Tag(name = "헬스 체크")
@MvcApiController
class HealthCheckApi {
    @Operation(summary = "헬스 체크")
    @GetMapping("/v1/health")
    fun healthCheck() = "I`m Health :)"

    @Secured(AuthRole.USER)
    @Operation(summary = "인증 체크")
    @GetMapping("/v1/health/auth")
    fun authHealthCheck(@AuthenticationPrincipal user: AuthUser) = "Hi! my username is ${user.username} :)"
}
