package com.jinuk.toy.domain.user.value

import com.fasterxml.jackson.annotation.JsonCreator

@JvmInline
value class Username private constructor(val value: String) {
    companion object {
        @JsonCreator
        fun from(title: String) = Username(title)

        operator fun invoke(title: String) = from(title)
    }

    init {
        validate(value)
    }

    private fun validate(title: String) {
        require(title.length in 3..20) { "사용자 이름은 3자 이상 20자 이하여야 합니다." }
        require(title.matches(Regex("^[a-zA-Z0-9_]*$"))) { "사용자 이름은 알파벳, 숫자, 밑줄(_)만 사용할 수 있습니다." }
    }
}
