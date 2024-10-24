package com.jinuk.toy.mvcapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["com.jinuk.toy"],
    exclude = [
        DataSourceAutoConfiguration::class,
    ],
)
class MvcAPIApplication

fun main(args: Array<String>) {
    runApplication<MvcAPIApplication>(*args)
}
