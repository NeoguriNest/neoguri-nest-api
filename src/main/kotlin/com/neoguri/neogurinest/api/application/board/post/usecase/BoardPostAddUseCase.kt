package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostAddDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException

interface BoardPostAddUseCase {

    @Throws(BoardChannelNotFoundException::class, BoardChannelNotAvailableStatusException::class)
    fun execute(addDto: BoardPostAddDto, actor: BoardActor?): BoardPostDto
}