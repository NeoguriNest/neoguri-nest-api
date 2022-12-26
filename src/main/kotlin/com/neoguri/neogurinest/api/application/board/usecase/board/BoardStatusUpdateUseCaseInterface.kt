package com.neoguri.neogurinest.api.application.board.usecase.board

import com.neoguri.neogurinest.api.application.board.dto.request.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
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