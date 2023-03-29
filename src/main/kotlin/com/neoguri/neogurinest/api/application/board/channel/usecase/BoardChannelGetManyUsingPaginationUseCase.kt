package com.neoguri.neogurinest.api.application.board.channel.usecase

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto
import com.neoguri.neogurinest.api.application.board.usecase.GetManyUsingPaginationUseCase

interface BoardChannelGetManyUsingPaginationUseCase : GetManyUsingPaginationUseCase<BoardChannelFilterDto, BoardChannelDto> {

}