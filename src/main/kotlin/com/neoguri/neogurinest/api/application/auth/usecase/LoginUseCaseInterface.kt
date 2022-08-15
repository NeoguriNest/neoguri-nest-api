package com.neoguri.neogurinest.api.application.auth.usecase

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.user.dto.request.LoginDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.LoginUserDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException

interface LoginUseCaseInterface {
    @Throws(UsernameOrPasswordNotMatchedException::class)
    fun execute(loginDto: LoginDto): AuthorizationDto
}
