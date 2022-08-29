package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class BadRequestException(override val message: String) : HttpException(HttpStatus.BAD_REQUEST, message)  {

}