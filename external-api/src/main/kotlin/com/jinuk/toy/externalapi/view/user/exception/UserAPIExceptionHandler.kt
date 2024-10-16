package com.jinuk.toy.externalapi.view.user.exception

import com.jinuk.toy.externalapi.global.exception.ErrorResponse
import com.jinuk.toy.externalapi.view.user.UserAPI
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [UserAPI::class])
class UserAPIExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException) = ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.from(e.message))

    @ExceptionHandler(NoSuchElementException::class)
    fun handleIllegalArgumentException(e: NoSuchElementException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.from(e.message))
}
