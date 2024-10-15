package com.jinuk.toy.util.domainhelper

import java.time.LocalDateTime

abstract class BaseDomain(
    open val _id: Long? = null,
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    open val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val id: Long
        get() = _id ?: throw IllegalStateException("ID가 아직 초기화되지 않았습니다.")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as BaseDomain
        return _id == other._id
    }

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }
}
