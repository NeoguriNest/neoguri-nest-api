package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostActorDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostAddDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardNotAvailableStatusException
import javax.persistence.EntityNotFoundException

interface BoardPostAddUseCaseInterface {

    @Throws(EntityNotFoundException::class, BoardNotAvailableStatusException::class)
    fun execute(addDto: BoardPostAddDto, actor: BoardPostActorDto?): BoardPostDto
}