package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddressUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
@EnableRetry
class UserAddressUpdateUseCase(
    val userRepository: UserEntityRepositoryInterface
) : UserAddressUpdateUseCaseInterface {
    override fun execute(addressUpdateDto: UserAddressUpdateDto): UserDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional(isolation = Isolation.READ_COMMITTED)
            fun(updateDto: UserAddressUpdateDto): UserDto {
                val user: User = userRepository.findByIdOrFail(updateDto.userId)
                user.updateAddress(updateDto)

                return UserDto.of(userRepository.save(user))
            }

        try {
            return closure(addressUpdateDto)
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw e
        }
    }
}