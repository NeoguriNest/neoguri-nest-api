package com.neoguri.neogurinest.api.application.board.usecase

import com.neoguri.neogurinest.api.application.board.dto.request.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface BoardStatusUpdateUseCaseInterface {

    @Throws(DuplicatedEntityException::class)
    fun execute(updateDto: BoardStatusUpdateDto): BoardDto
}