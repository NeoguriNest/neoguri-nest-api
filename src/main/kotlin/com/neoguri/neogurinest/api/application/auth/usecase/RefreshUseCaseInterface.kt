package com.neoguri.neogurinest.api.application.auth.usecase

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.application.auth.dto.RefreshDto
import com.neoguri.neogurinest.api.domain.auth.exception.UsernameOrPasswordNotMatchedException
import org.springframework.web.bind.annotation.RequestBody

interface RefreshUseCaseInterface {
    @Throws(UsernameOrPasswordNotMatchedException::class)
    fun execute(refreshDto: RefreshDto): AuthorizationDto
}
