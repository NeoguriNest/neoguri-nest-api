package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserExistenceCheckDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto

interface UserExistenceCheckUseCaseInterface {

    fun execute(checkDto: UserExistenceCheckDto): Boolean
}