package com.jinuk.toy.externalapi.view.health

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "헬스 체크")
@RestController
class HealthCheckAPI {

    @Operation(summary = "헬스 체크")
    @GetMapping("/health")
    fun healthCheck() = "I`m Health :)"
}
