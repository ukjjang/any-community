package com.jinuk.toy.common.define.user

import com.fasterxml.jackson.annotation.JsonCreator

@JvmInline
value class RawPassword private constructor(val value: String) {
    companion object {
        @JsonCreator
        fun from(password: String) = RawPassword(password)

        operator fun invoke(password: String) = from(password)
    }

    init {
        validate(value)
    }

    private fun validate(password: String) {
        require(password.length in 8..20) { "비밀번호는 8자 이상 20자 이하여야 합니다." }
        require(password.any { it.isUpperCase() }) { "비밀번호는 최소 하나의 대문자가 포함되어야 합니다." }
        require(password.any { it.isLowerCase() }) { "비밀번호는 최소 하나의 소문자가 포함되어야 합니다." }
        require(password.any { it.isDigit() }) { "비밀번호는 최소 하나의 숫자가 포함되어야 합니다." }
    }
}
