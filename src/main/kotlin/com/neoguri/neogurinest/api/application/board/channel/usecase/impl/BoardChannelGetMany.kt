package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelGetManyUseCase
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.persistence.specification.board.channel.BoardChannelSpecification
import org.springframework.stereotype.Service

@Service
class BoardChannelGetMany(
    val channelRepository: BoardChannelEntityRepositoryInterface
) : BoardChannelGetManyUseCase {

    override fun execute(filterDto: BoardChannelFilterDto): List<BoardChannelDto> {

        val specification = BoardChannelSpecification.of(filterDto)
        val channels: List<BoardChannel> = channelRepository.findBySpecification(specification, 100)

        return channels.map {
            BoardChannelDto.of(it)
        }
    }
}