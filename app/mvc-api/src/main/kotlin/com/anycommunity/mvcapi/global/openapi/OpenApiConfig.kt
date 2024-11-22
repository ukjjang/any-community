package com.anycommunity.mvcapi.global.openapi

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.SpecVersion
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openApi(): OpenAPI = OpenAPI().apply {
        specVersion(SpecVersion.V30)
        info(Info().title("MVC API").version("1.0"))
        addSecurityItem(SecurityRequirement().addList("bearerAuth"))
        components(
            Components().addSecuritySchemes(
                "bearerAuth",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"),
            ),
        )
    }

    @Bean
    fun mvcApi(): GroupedOpenApi = GroupedOpenApi.builder().apply {
        group("mvc-api")
        displayName("Mvc API")
        packagesToScan("com.anycommunity.mvcapi")
    }.build()
}
