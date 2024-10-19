package com.jinuk.toy.infra.rdb

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class RdbRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey() = if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) "read" else "write"
}
