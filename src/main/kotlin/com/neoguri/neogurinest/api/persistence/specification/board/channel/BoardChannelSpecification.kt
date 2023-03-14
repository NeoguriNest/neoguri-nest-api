package com.neoguri.neogurinest.api.persistence.specification.board.channel

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.persistence.specification.ObjectSpecificationBuilder
import org.springframework.data.jpa.domain.Specification

class BoardChannelSpecification {

    companion object {
        fun of(filterDto: BoardChannelFilterDto): Specification<BoardChannel>? {
            return ObjectSpecificationBuilder<BoardChannelFilterDto, BoardChannel>(BoardChannelFilterDto::class.java)
                .build(filterDto)
        }
    }

}