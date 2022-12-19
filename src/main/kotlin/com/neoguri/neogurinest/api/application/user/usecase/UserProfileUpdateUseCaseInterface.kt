package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserProfileUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import javax.persistence.EntityNotFoundException

interface UserProfileUpdateUseCaseInterface {
    @Throws(EntityNotFoundException::class)
    fun execute(userProfileUpdateDto: UserProfileUpdateDto): UserDto
}