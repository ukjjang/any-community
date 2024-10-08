package com.jinuk.toy.externalapi.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckAPI {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "I`m Health"
    }
}
