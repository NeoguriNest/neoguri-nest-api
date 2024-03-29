package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class ConflictException(override val message: String) : HttpException(HttpStatus.CONFLICT, message) {

}