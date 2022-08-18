package com.neoguri.neogurinest.api.application.auth.usecase

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException

interface LoginUseCaseInterface {
    @Throws(UsernameOrPasswordNotMatchedException::class)
    fun execute(loginDto: LoginDto): AuthorizationDto
}
