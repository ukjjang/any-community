package com.jinuk.toy.externalapi.global.openapi

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.SpecVersion
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .specVersion(SpecVersion.V30)
            .info(
                Info()
                    .title("EXTERNAL API")
                    .version("1.0"),
            )
            .servers(
                listOf(
                    Server().url("/"),
                ),
            )
    }

    @Bean
    fun externalApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("external-api")
            .displayName("External API")
            .packagesToScan("com.jinuk.toy.externalapi")
            .build()
    }
}
