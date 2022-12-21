package com.neoguri.neogurinest.api.application.board.usecase.impl

import com.neoguri.neogurinest.api.application.board.dto.request.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
import com.neoguri.neogurinest.api.application.board.usecase.BoardStatusUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class BoardStatusUpdateUseCase(
    val boardRepository: BoardEntityRepositoryInterface
) : BoardStatusUpdateUseCaseInterface {

    @Throws(EntityNotFoundException::class)
    override fun execute(updateDto: BoardStatusUpdateDto): BoardDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun (updateDto: BoardStatusUpdateDto): BoardDto {
                val board = boardRepository.findByIdOrFail(updateDto.boardId)

                board.status = updateDto.status
                return BoardDto.of(boardRepository.save(board))
            }

        return closure(updateDto)
    }

}