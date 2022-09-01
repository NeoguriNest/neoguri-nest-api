package com.neoguri.neogurinest.api.presentation.error

import org.springframework.http.HttpStatus
import java.time.Instant

data class ErrorResponseDto(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String?
) {

    companion object {
        fun of(status: HttpStatus, message: String?): ErrorResponseDto {
            return ErrorResponseDto(
                Instant.now().toString(),
                status.value(),
                status.reasonPhrase,
                message
            )
        }
    }

}