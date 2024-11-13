package com.jinuk.toy.common.value.post

@JvmInline
value class PostTitle(
    val value: String,
) {
    init {
        validate(value)
    }

    private fun validate(title: String) {
        require(title.isNotBlank()) { "게시글의 제목은 비어있을 수 없습니다." }
        require(title.length <= 50) { "게시글의 제목은 50자를 초과할 수 없습니다." }
    }
}
