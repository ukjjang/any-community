package com.jinuk.toy.constant.user

enum class Gender {
    MALE,
    FEMALE,
    ;

    companion object {
        fun from(name: String) = Gender.entries.first { it.name == name }
    }
}
