package com.jinuk.toy.externalapi.view.health

import com.jinuk.toy.externalapi.global.ExternalAPIController
import com.jinuk.toy.externalapi.global.security.AuthRole
import com.jinuk.toy.externalapi.global.security.AuthUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping

@Tag(name = "헬스 체크")
@ExternalAPIController
class HealthCheckAPI {

    @Operation(summary = "헬스 체크")
    @GetMapping("/v1/health")
    fun healthCheck() = "I`m Health :)"

    @Secured(AuthRole.USER)
    @Operation(summary = "인증 체크")
    @GetMapping("/v1/health/auth")
    fun authHealthCheck(
        @AuthenticationPrincipal user: AuthUser,
    ) = "Hi! my username is ${user.username} :)"
}
