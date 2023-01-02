package com.neoguri.neogurinest.api.application.board.channel.usecase.board

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import javax.persistence.EntityNotFoundException

interface BoardStatusUpdateUseCaseInterface {

    @Throws(
        EntityNotFoundException::class,
        StatusAlreadyChangedException::class,
        BoardStatusNotConvertableException::class
    )
    fun execute(updateDto: BoardStatusUpdateDto): BoardDto
}