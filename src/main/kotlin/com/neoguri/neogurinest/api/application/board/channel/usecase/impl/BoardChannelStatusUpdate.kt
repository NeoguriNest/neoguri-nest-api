package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelStatusUpdateUseCase
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class BoardChannelStatusUpdate(
    val boardRepository: BoardChannelEntityRepositoryInterface
) : BoardChannelStatusUpdateUseCase {

    @Throws(
        EntityNotFoundException::class,
        StatusAlreadyChangedException::class,
        BoardChannelStatusNotConvertableException::class
    )
    override fun execute(updateDto: BoardStatusUpdateDto): BoardChannelDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun(updateDto: BoardStatusUpdateDto): BoardChannelDto {
                val board = boardRepository.findByIdOrFail(updateDto.boardId)

                if (updateDto.status === board.status) {
                    throw StatusAlreadyChangedException()
                }

                if (!board.isStatusConvertable(updateDto.status)) {
                    throw BoardChannelStatusNotConvertableException()
                }

                board.status = updateDto.status
                return BoardChannelDto.of(boardRepository.save(board))
            }

        return closure(updateDto)
    }

}