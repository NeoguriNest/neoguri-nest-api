package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserNestAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface UserNestAddUseCaseInterface {
    @Throws(DuplicatedEntityException::class)
    fun execute(userNestAddDto: UserNestAddDto): UserDto
}