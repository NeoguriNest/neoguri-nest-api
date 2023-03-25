package com.neoguri.neogurinest.api.application.board.channel.usecase

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto

interface BoardChannelGetUseCaseInterface {

    fun execute(channelId: String): BoardChannelDto
}