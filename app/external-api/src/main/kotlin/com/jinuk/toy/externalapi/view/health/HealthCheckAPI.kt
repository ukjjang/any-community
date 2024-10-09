package com.jinuk.toy.externalapi.view.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckAPI {

    @GetMapping("/health")
    fun healthCheck() = "I`m Health :)"
}
