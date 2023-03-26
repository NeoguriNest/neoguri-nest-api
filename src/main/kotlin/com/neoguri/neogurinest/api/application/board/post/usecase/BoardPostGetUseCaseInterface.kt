package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException

interface BoardPostGetUseCaseInterface {

    @Throws(BoardPostNotFoundException::class)
    fun execute(postId: String, actor: BoardActor?): BoardPostDto
}