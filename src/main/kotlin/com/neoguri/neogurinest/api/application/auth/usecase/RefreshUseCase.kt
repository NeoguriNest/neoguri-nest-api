package com.neoguri.neogurinest.api.application.auth.usecase

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.RefreshDto
import com.neoguri.neogurinest.api.domain.auth.exception.RefreshTokenExpiredException

interface RefreshUseCase {
    @Throws(RefreshTokenExpiredException::class)
    fun execute(refreshDto: RefreshDto): AuthorizationDto
}
