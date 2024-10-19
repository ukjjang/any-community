package com.jinuk.toy.infra.rdb

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class RdbConfig : HikariConfig() {
    @Bean
    @ConfigurationProperties(prefix = "datasource.rdb.writer")
    fun writerDataSource(): DataSource {
        return HikariDataSource()
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.rdb.reader")
    fun readerDataSource(): DataSource {
        return HikariDataSource()
    }

    @Bean
    fun routingDataSource(
        @Qualifier("writerDataSource") writerDataSource: DataSource,
        @Qualifier("readerDataSource") readerDataSource: DataSource,
    ): DataSource {
        val dataSourceMap: Map<Any, Any> =
            mutableMapOf(
                "write" to writerDataSource,
                "read" to readerDataSource,
            )

        val dataSource = RdbRoutingDataSource()
        dataSource.setTargetDataSources(dataSourceMap)
        dataSource.setDefaultTargetDataSource(writerDataSource)

        // loading slave connection
        val slaveConnection = readerDataSource.connection
        slaveConnection.close()

        return dataSource
    }

    @Primary
    @Bean
    @DependsOn("writerDataSource", "readerDataSource", "routingDataSource")
    fun dataSource(
        @Qualifier("routingDataSource") routingDataSource: DataSource,
    ): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }

    @Primary
    @Bean
    fun entityManagerFactory(
        entityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("dataSource") dataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean {
        return entityManagerFactoryBuilder
            .dataSource(dataSource)
            .packages("com.jinuk.toy.**.entity")
            .persistenceUnit("rdb")
            .build()
    }

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory,
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
