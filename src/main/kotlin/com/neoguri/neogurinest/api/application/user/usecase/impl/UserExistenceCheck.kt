package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserExistenceCheckDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserExistenceDto
import com.neoguri.neogurinest.api.application.user.usecase.UserExistenceCheckUseCase
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.springframework.stereotype.Service

@Service
class UserExistenceCheck(
    private val userRepository: UserEntityRepositoryInterface
) : UserExistenceCheckUseCase {

    override fun execute(checkDto: UserExistenceCheckDto): UserExistenceDto {

        val exists: Boolean = userRepository.checkExistsByEmail(checkDto.email)

        return if (!exists) {
            UserExistenceDto.ofUnregistered()
        } else {
            val user: User = userRepository.findByEmailOrFail(checkDto.email)

            UserExistenceDto.of(user)
        }

    }
}