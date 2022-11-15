package com.neoguri.neogurinest.api.presentation.error

import com.neoguri.neogurinest.api.presentation.exception.HttpException
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class NeoguriExceptionHandler {

    val logger: Logger = LoggerFactory.getLogger(this@NeoguriExceptionHandler::class.java)

    @ExceptionHandler(HttpException::class)
    fun handleHttpException(exception: HttpException): ResponseEntity<ErrorResponseDto> {
        logger.info("status code: {}\nstackTrace: {}", exception.message, exception.stackTrace)

        return ResponseEntity.status(exception.statusCode).body(
            ErrorResponseDto.of(
                exception.statusCode,
                exception.message
            )
        )
    }

    /**
     * @desc RequestHandler 진입 시 NonNull 타입에서 NullPointerException 발생하는 경우 400 BAD_REQUEST 응답
     */
    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointer(exception: NullPointerException): ResponseEntity<ErrorResponseDto> {
        logger.info("status code: {}\nstackTrace: {}", exception.message, exception.stackTrace)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponseDto.of(
                HttpStatus.BAD_REQUEST,
                exception.message
            )
        )
    }

}