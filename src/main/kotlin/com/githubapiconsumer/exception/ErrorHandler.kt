package com.githubapiconsumer.exception

import com.githubapiconsumer.model.ErrorResponseDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ErrorHandler {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = [GhUserNotFoundException::class])
    @ResponseBody
    fun handleBadRequest(e: Exception) =
        ErrorResponseDto(status = NOT_FOUND.value(), message = "Resource not found: ${e.message}")

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(UnsupportedContentTypeException::class)
    @Order(HIGHEST_PRECEDENCE)
    @ResponseBody
    fun handleNotAcceptableStatusException(ex: UnsupportedContentTypeException): ResponseEntity<ErrorResponseDto> {
        return ResponseEntity.status(406).body(
            ErrorResponseDto(
                status = NOT_ACCEPTABLE.value(),
                message = "Requested media type is not supported"
            )
        )
    }

}