package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class ConflictException() : HttpException(HttpStatus.CONFLICT.name) {

    override fun getStatusCode(): HttpStatus {
        return HttpStatus.CONFLICT
    }
}