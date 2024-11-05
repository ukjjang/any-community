package com.jinuk.toy.constant.like

enum class LikeType {
    POST,
    COMMENT,
    ;

    companion object {
        fun from(name: String) = entries.first { it.name == name }
    }
}
