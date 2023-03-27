package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelAddUseCase
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardChannelAdd(
    val boardRepository: BoardChannelEntityRepositoryInterface
) : BoardChannelAddUseCase {

    @Throws(DuplicatedEntityException::class)
    override fun execute(addDto: BoardAddDto): BoardChannelDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun (addDto: BoardAddDto): BoardChannelDto {

                val channel = boardRepository.save(BoardChannel.create(addDto))
                return BoardChannelDto.of(channel)
            }

        return try {
            closure(addDto)
        } catch (e: DataIntegrityViolationException) {
            if (ConstraintViolationException::class.java.isAssignableFrom(e.cause!!::class.java)
                && (e.cause as ConstraintViolationException).constraintName.contains("UNIQUE")) {

                throw DuplicatedEntityException()
            }

            throw e
        }
    }

}