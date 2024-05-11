package com.example.nong9server.common.infrastructure

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

fun <T> responseEntity(block: ResponseEntityBuilder<T>.() -> Unit): ResponseEntity<T> {
    val responseEntityBuilder = ResponseEntityBuilder<T>()
    responseEntityBuilder.block()
    return responseEntityBuilder.build()
}


@NokDslMarker
data class ResponseEntityBuilder<T>(
    var status: HttpStatus = HttpStatus.OK,
    var contentType: MediaType = MediaType.APPLICATION_JSON,
    var body: T? = null
) {
    fun build() = ResponseEntity.status(status)
        .contentType(contentType)
        .body(body)
}

object EmptyBody

@DslMarker
annotation class NokDslMarker
