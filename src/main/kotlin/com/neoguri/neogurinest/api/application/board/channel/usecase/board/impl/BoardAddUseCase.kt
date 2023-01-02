package com.neoguri.neogurinest.api.application.board.channel.usecase.board.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.board.BoardAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.entity.Board
import com.neoguri.neogurinest.api.domain.board.repository.BoardEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardAddUseCase(
    val boardRepository: BoardEntityRepositoryInterface
) : BoardAddUseCaseInterface {

    @Throws(DuplicatedEntityException::class)
    override fun execute(addDto: BoardAddDto): BoardDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun (addDto: BoardAddDto): BoardDto {

                val board = boardRepository.save(Board.create(addDto))
                return BoardDto.of(board)
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