package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException

interface BoardPostGetUseCase {

    @Throws(BoardPostNotFoundException::class)
    fun execute(postId: String, actor: BoardActor?): BoardPostDto
}