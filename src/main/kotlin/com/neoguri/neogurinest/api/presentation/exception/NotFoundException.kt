package com.neoguri.neogurinest.api.presentation.exception

import org.springframework.http.HttpStatus

class NotFoundException(override val message: String) : HttpException(HttpStatus.NOT_FOUND, message)  {

}