package com.neoguri.neogurinest.api.application.board.usecase

import com.neoguri.neogurinest.api.application.board.dto.request.BoardAddDto
import com.neoguri.neogurinest.api.application.board.dto.response.BoardDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface BoardAddUseCaseInterface {

    @Throws(DuplicatedEntityException::class)
    fun execute(addDto: BoardAddDto): BoardDto
}