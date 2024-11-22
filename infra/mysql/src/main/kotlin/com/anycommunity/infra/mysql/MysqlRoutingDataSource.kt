package com.anycommunity.infra.mysql

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class MysqlRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey() =
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) "read" else "write"
}
