package com.jinuk.toy.domain.like

enum class LikeType {
    POST,
    COMMENT,
    ;

    companion object {
        fun from(name: String) = entries.first { it.name == name }
    }
}
