package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostUpdateDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException

interface BoardPostUpdateUseCaseInterface {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class)
    fun execute(updateDto: BoardPostUpdateDto): BoardPostDto
}