package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.UserDto

interface UserAddUseCaseInterface {
    fun execute(userAddDto: UserAddDto): UserDto
}