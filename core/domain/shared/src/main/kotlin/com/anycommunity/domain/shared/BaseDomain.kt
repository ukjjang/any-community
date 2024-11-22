package com.anycommunity.domain.shared

import java.time.LocalDateTime

abstract class BaseDomain(
    open val _id: Long? = null,
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    open val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    val id: Long
        get() = _id ?: throw IllegalStateException("ID가 아직 초기화되지 않았습니다.")
}
