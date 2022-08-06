package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface UserAddUseCaseInterface {
    @Throws(DuplicatedEntityException::class)
    fun execute(userAddDto: UserAddDto): UserDto
}