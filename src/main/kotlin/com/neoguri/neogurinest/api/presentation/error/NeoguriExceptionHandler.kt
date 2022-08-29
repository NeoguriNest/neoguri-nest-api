package com.neoguri.neogurinest.api.presentation.error

import com.neoguri.neogurinest.api.presentation.exception.HttpException
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant


@Slf4j
@RestControllerAdvice
class NeoguriExceptionHandler {

    val logger: Logger = LoggerFactory.getLogger(this@NeoguriExceptionHandler::class.java)

    @ExceptionHandler(HttpException::class)
    fun handleHttpException(exception: HttpException): ResponseEntity<ErrorResponseDto> {
        logger.info("status code: {}\nstackTrace: {}", exception.message, exception.stackTrace)

        return ResponseEntity.status(exception.statusCode).body(
            ErrorResponseDto(
                Instant.now().toString(),
                exception.statusCode.value(),
                exception.statusCode.reasonPhrase,
                exception.message
            )
        )
    }

}