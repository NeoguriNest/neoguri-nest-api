package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserProfileUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface UserProfileUpdateUseCaseInterface {
    @Throws(DuplicatedEntityException::class)
    fun execute(userProfileUpdateDto: UserProfileUpdateDto): UserDto
}