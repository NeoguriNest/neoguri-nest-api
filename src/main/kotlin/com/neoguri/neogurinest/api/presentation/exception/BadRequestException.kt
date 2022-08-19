package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class BadRequestException() : HttpException(HttpStatus.BAD_REQUEST.name) {

    override fun getStatusCode(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}