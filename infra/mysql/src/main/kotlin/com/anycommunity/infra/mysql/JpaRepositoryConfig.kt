package com.anycommunity.infra.mysql

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["com.anycommunity.infra.mysql.**.jpa"],
)
class JpaRepositoryConfig
