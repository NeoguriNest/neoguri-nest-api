package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UserAddUseCase(
    val userRepository: UserEntityRepositoryInterface
) : UserAddUseCaseInterface {
    override fun execute(userAddDto: UserAddDto): UserDto {

        val closure =
            @Transactional(isolation = Isolation.READ_COMMITTED)
            fun(addDto: UserAddDto): UserDto {
                return UserDto.fromEntity(
                    userRepository.save(User.create(addDto))
                )
            }

        try {
            return closure(userAddDto)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicatedEntityException()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e;
        }
    }
}