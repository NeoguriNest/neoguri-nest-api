package com.neoguri.neogurinest.api.application.board.channel.usecase

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto

interface BoardChannelGetManyUseCaseInterface {

    fun execute(filterDto: BoardChannelFilterDto): List<BoardChannelDto>
}