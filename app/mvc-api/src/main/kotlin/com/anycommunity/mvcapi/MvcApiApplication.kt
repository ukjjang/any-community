package com.anycommunity.mvcapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.anycommunity"],
    exclude = [
        DataSourceAutoConfiguration::class,
    ],
)
class MvcApiApplication

fun main(args: Array<String>) {
    runApplication<MvcApiApplication>(*args)
}
