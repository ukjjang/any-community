package com.jinuk.toy.mvcapi.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import com.jinuk.toy.mvcapi.auth.AuthAPI
import com.jinuk.toy.mvcapi.global.exception.ErrorResponse

@RestControllerAdvice(assignableTypes = [AuthAPI::class])
class AuthAPIExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException) = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.from(e.message))

    @ExceptionHandler(NoSuchElementException::class)
    fun handleIllegalArgumentException(e: NoSuchElementException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.from(e.message))
}
