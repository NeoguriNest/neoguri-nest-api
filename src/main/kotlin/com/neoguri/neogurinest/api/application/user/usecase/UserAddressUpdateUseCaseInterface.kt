package com.neoguri.neogurinest.api.application.user.usecase

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto

interface UserAddressUpdateUseCaseInterface {

    fun execute(addressUpdateDto: UserAddressUpdateDto): UserDto
}