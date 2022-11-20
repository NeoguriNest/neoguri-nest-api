package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.UserNestAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserNestAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.nest.repository.NestEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.entity.UserNest
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import lombok.extern.slf4j.Slf4j
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@EnableRetry
class UserNestAddUseCase(
    val userRepository: UserEntityRepositoryInterface,
    val nestRepository: NestEntityRepositoryInterface
) : UserNestAddUseCaseInterface {
    override fun execute(userNestAddDto: UserNestAddDto): UserDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional(isolation = Isolation.READ_COMMITTED)
            fun(userNestAddDto: UserNestAddDto): UserDto {
                val user: User = userRepository.findByIdOrFail(userNestAddDto.userId)

                val nest: Nest = nestRepository.findByIdOrFail(userNestAddDto.nestId)

                user.addUserNest(UserNest.create(userNestAddDto))

                return UserDto.of(userRepository.save(user))
            }

        try {
            return closure(userNestAddDto)
        } catch (e: DataIntegrityViolationException) {
            if (ConstraintViolationException::class.java.isAssignableFrom(e.cause!!::class.java)
                && (e.cause as ConstraintViolationException).constraintName.contains("UNIQUE")) {
                throw DuplicatedEntityException()
            }

            throw e
        }
    }
}