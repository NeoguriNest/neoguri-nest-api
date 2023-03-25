package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelGetUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import org.springframework.stereotype.Service

@Service
class BoardChannelGetUseCase(
    val channelRepository: BoardChannelEntityRepositoryInterface
) : BoardChannelGetUseCaseInterface {

    override fun execute(channelId: String): BoardChannelDto {

        val channel: BoardChannel = channelRepository.findByIdOrFail(channelId)

        return BoardChannelDto.of(channel)
    }
}