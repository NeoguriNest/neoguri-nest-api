package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserExistenceCheckDto
import com.neoguri.neogurinest.api.application.user.usecase.UserExistenceCheckUseCaseInterface
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface

class UserExistenceCheckUseCase(
    private val userRepository: UserEntityRepositoryInterface
) : UserExistenceCheckUseCaseInterface {

    override fun execute(checkDto: UserExistenceCheckDto): Boolean {
        return userRepository.checkExistsByEmail(checkDto.email);
    }
}