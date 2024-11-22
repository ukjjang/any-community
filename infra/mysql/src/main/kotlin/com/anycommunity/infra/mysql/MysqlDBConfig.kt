package com.anycommunity.infra.mysql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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
import jakarta.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class MysqlDBConfig : HikariConfig() {
    @Bean
    @ConfigurationProperties(prefix = "datasource.mysql.writer")
    fun writerDataSource(): DataSource = HikariDataSource()

    @Bean
    @ConfigurationProperties(prefix = "datasource.mysql.reader")
    fun readerDataSource(): DataSource = HikariDataSource()

    @Bean
    fun routingDataSource(
        @Qualifier("writerDataSource") writerDataSource: DataSource,
        @Qualifier("readerDataSource") readerDataSource: DataSource,
    ): DataSource {
        val dataSourceMap: Map<Any, Any> = mutableMapOf(
            "write" to writerDataSource,
            "read" to readerDataSource,
        )
        1
        val dataSource = MysqlRoutingDataSource()
        dataSource.setTargetDataSources(dataSourceMap)
        dataSource.setDefaultTargetDataSource(writerDataSource)

        val slaveConnection = readerDataSource.connection
        slaveConnection.close()

        return dataSource
    }

    @Primary
    @Bean
    @DependsOn("writerDataSource", "readerDataSource", "routingDataSource")
    fun dataSource(@Qualifier("routingDataSource") routingDataSource: DataSource): DataSource =
        LazyConnectionDataSourceProxy(routingDataSource)

    @Primary
    @Bean
    fun entityManagerFactory(
        entityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("dataSource") dataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean = entityManagerFactoryBuilder
        .dataSource(dataSource)
        .packages("com.anycommunity.**.entity")
        .persistenceUnit("mysqlEntityManager")
        .build()

    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory,
    ): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory)
}
