package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostUpdateDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import javax.persistence.EntityNotFoundException

interface BoardPostUpdateUseCaseInterface {

    @Throws(EntityNotFoundException::class, BoardPostCannotUpdateException::class)
    fun execute(updateDto: BoardPostUpdateDto): BoardPostDto
}