package com.anycommunity.definition.user

@JvmInline
value class RawPassword(
    val value: String,
) {
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
