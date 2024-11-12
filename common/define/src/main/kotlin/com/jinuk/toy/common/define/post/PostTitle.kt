package com.jinuk.toy.common.define.post

import com.fasterxml.jackson.annotation.JsonCreator

@JvmInline
value class PostTitle private constructor(val value: String) {
    companion object {
        @JsonCreator
        fun from(title: String) = PostTitle(title)

        operator fun invoke(title: String) = from(title)
    }

    init {
        validate(value)
    }

    private fun validate(title: String) {
        require(title.isNotBlank()) { "게시글의 제목은 비어있을 수 없습니다." }
        require(title.length <= 50) { "게시글의 제목은 50자를 초과할 수 없습니다." }
    }
}
