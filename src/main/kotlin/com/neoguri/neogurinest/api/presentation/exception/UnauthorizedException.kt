package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class UnauthorizedException() : HttpException(HttpStatus.UNAUTHORIZED.name) {

    override fun getStatusCode(): HttpStatus {
        return HttpStatus.UNAUTHORIZED
    }
}