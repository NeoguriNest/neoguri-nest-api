package com.neoguri.neogurinest.api.application.board.channel.usecase.board

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface BoardAddUseCaseInterface {

    @Throws(DuplicatedEntityException::class)
    fun execute(addDto: BoardAddDto): BoardDto
}