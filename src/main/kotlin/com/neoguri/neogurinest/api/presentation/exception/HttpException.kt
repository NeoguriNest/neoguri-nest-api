package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

abstract class HttpException(override val message: String) : RuntimeException(message) {

    abstract fun getStatusCode(): HttpStatus;
}