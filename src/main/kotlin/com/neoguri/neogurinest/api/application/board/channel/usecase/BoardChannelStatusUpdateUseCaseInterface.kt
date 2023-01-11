package com.neoguri.neogurinest.api.application.board.channel.usecase

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardStatusUpdateDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import javax.persistence.EntityNotFoundException

interface BoardChannelStatusUpdateUseCaseInterface {

    @Throws(
        EntityNotFoundException::class,
        StatusAlreadyChangedException::class,
        BoardChannelStatusNotConvertableException::class
    )
    fun execute(updateDto: BoardStatusUpdateDto): BoardChannelDto
}