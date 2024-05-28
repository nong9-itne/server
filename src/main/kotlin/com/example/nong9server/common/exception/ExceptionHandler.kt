package com.example.nong9server.common.exception

import com.example.nong9server.common.presentation.responseEntity
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        httpHeaders: HttpHeaders,
        httpStatus: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        val messages = when (val exception = ex.cause) {
            is MismatchedInputException -> "${exception.path.last().fieldName.orEmpty()}: 올바른 형식이여야 합니다"
            else -> exception?.message.orEmpty()
        }
        return responseEntity {
            body = messages
            status = HttpStatus.BAD_REQUEST
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        httpStatus: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message", ex)
        return responseEntity {
            body = ex.messages()
            status = HttpStatus.BAD_REQUEST
        }
    }

    private fun MethodArgumentNotValidException.messages(): String {
        return bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage.orEmpty()}" }
    }

    @ExceptionHandler(TokenException::class, UnidentifiedMemberException::class)
    fun handleUnauthorizedException(exception: RuntimeException): ResponseEntity<Any> {
        logger.error("message", exception)
        return responseEntity {
            body = exception.message
            status = HttpStatus.UNAUTHORIZED
        }
    }

    @ExceptionHandler(MemberNotFoundException::class, DuplicateMemberException::class)
    fun handleBadRequestException(exception: RuntimeException): ResponseEntity<Any> {
        logger.error("message", exception)
        return responseEntity {
            body = exception.message
            status = HttpStatus.BAD_REQUEST
        }
    }
}
