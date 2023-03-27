package com.neoguri.neogurinest.api.application.auth.usecase

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.domain.auth.exception.UsernameOrPasswordNotMatchedException

interface LoginUseCase {
    @Throws(UsernameOrPasswordNotMatchedException::class)
    fun execute(loginDto: LoginDto): AuthorizationDto
}
