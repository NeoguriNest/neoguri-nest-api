package com.neoguri.neogurinest.api.application.board.channel.usecase

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardAddDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface BoardChannelAddUseCase {

    @Throws(DuplicatedEntityException::class)
    fun execute(addDto: BoardAddDto): BoardChannelDto
}