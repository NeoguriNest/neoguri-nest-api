package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class ForbiddenException(override val message: String) : HttpException(HttpStatus.FORBIDDEN, message) {
}