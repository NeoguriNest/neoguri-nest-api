package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCase
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UserAdd(
    val userRepository: UserEntityRepositoryInterface
) : UserAddUseCase {
    override fun execute(userAddDto: UserAddDto): UserDto {

        val closure =
            @Transactional(isolation = Isolation.READ_COMMITTED)
            @Retryable(maxAttempts = 3)
            fun(addDto: UserAddDto): UserDto {
                return UserDto.of(
                    userRepository.save(User.create(addDto))
                )
            }

        return try {
            closure(userAddDto)
        } catch (e: DataIntegrityViolationException) {
            if (ConstraintViolationException::class.java.isAssignableFrom(e.cause!!::class.java)
                && (e.cause as ConstraintViolationException).constraintName.contains("UNIQUE")) {
                // constraint check

                throw DuplicatedEntityException()
            }

            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}