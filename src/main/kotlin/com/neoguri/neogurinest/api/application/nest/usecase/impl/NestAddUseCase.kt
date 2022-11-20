package com.neoguri.neogurinest.api.application.nest.usecase.impl

import com.neoguri.neogurinest.api.application.nest.dto.request.NestAddDto
import com.neoguri.neogurinest.api.application.nest.dto.response.NestDto
import com.neoguri.neogurinest.api.application.nest.usecase.NestAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.nest.repository.NestEntityRepositoryInterface
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class NestAddUseCase(
    val nestEntityRepository: NestEntityRepositoryInterface
) : NestAddUseCaseInterface {

    @Throws(DuplicatedEntityException::class)
    override fun execute(nestAddDto: NestAddDto): NestDto {

        val closure =
            @Transactional(isolation = Isolation.READ_COMMITTED)
            @Retryable(maxAttempts = 3)
            fun (nestAddDto: NestAddDto): NestDto {

            return NestDto.of(
                nestEntityRepository.save(Nest.create(nestAddDto))
            )
        }

        return try {
            closure(nestAddDto)
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