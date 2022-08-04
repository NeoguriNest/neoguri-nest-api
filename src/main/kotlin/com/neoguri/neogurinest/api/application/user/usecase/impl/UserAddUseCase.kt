package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepository
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import org.springframework.stereotype.Service

@Service
class UserAddUseCase(val userRepository: UserEntityRepositoryInterface) : UserAddUseCaseInterface {
    override fun execute(userAddDto: UserAddDto): UserDto {
        val user = User.create(userAddDto)

        try {
            userRepository.save(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return UserDto.fromEntity(user)
    }
}