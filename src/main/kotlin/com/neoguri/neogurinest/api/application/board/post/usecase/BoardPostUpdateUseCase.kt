package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostUpdateDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException

interface BoardPostUpdateUseCase {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class)
    fun execute(updateDto: BoardPostUpdateDto, actor: BoardActor): BoardPostDto
}