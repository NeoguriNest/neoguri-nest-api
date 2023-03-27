package com.neoguri.neogurinest.api.application.nest.usecase.impl

import com.neoguri.neogurinest.api.application.nest.dto.request.NestStatusUpdateDto
import com.neoguri.neogurinest.api.application.nest.dto.response.NestDto
import com.neoguri.neogurinest.api.application.nest.usecase.NestStatusUpdateUseCase
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
class NestStatusUpdate(
    val nestRepository: NestEntityRepositoryInterface
) : NestStatusUpdateUseCase {

    @Throws(DuplicatedEntityException::class)
    override fun execute(updateDto: NestStatusUpdateDto): NestDto {

        val closure =
            @Transactional(isolation = Isolation.READ_COMMITTED)
            @Retryable(maxAttempts = 3)
            fun (updateDto: NestStatusUpdateDto): NestDto {
                val nest: Nest = nestRepository.findByIdOrFail(updateDto.nestId)

                nest.status = updateDto.status

                return NestDto.of(nestRepository.save(nest))
            }

        return try {
            closure(updateDto)
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