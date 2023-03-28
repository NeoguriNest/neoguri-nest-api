package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostStatusUpdateDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException

interface BoardPostStatusUpdateUseCase {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class)
    fun execute(statusUpdateDto: BoardPostStatusUpdateDto, actor: BoardActor): BoardPostDto
}