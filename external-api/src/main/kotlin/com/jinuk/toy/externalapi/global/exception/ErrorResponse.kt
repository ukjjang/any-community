package com.jinuk.toy.externalapi.global.exception

data class ErrorResponse(
    val message: String,
) {
    companion object {
        fun from(message: String?) = ErrorResponse(message ?: "잘못된 요청입니다.")
    }
}
