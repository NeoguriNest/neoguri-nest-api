package com.neoguri.neogurinest.api.application.board.usecase.board.impl

import com.neoguri.neogurinest.api.application.board.dto.request.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
import com.neoguri.neogurinest.api.application.board.usecase.board.BoardStatusUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import com.neoguri.neogurinest.api.domain.board.exception.BoardStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.board.repository.BoardEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class BoardStatusUpdateUseCase(
    val boardRepository: BoardEntityRepositoryInterface
) : BoardStatusUpdateUseCaseInterface {

    @Throws(
        EntityNotFoundException::class,
        StatusAlreadyChangedException::class,
        BoardStatusNotConvertableException::class
    )
    override fun execute(updateDto: BoardStatusUpdateDto): BoardDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun(updateDto: BoardStatusUpdateDto): BoardDto {
                val board = boardRepository.findByIdOrFail(updateDto.boardId)

                if (updateDto.status === board.status) {
                    throw StatusAlreadyChangedException()
                }

                val preConvertStatus = BoardStatus.getConvertable(updateDto.status)
                if (!preConvertStatus.contains(board.status)) {
                    throw BoardStatusNotConvertableException()
                }

                board.status = updateDto.status
                return BoardDto.of(boardRepository.save(board))
            }

        return closure(updateDto)
    }

}