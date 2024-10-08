package com.jinuk.toy.infra.rdb

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["com.jinuk.toy.infra.rdb.**.jpa"],
)
class JpaRepositoryConfig
