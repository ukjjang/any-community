package com.anycommunity.definition.user

@JvmInline
value class Username(
    val value: String,
) {
    init {
        validate(value)
    }

    private fun validate(username: String) {
        require(username.length in 3..20) { "사용자 이름은 3자 이상 20자 이하여야 합니다." }
        require(username.matches(Regex("^[a-zA-Z0-9_]*$"))) { "사용자 이름은 알파벳, 숫자, 밑줄(_)만 사용할 수 있습니다." }
    }
}
