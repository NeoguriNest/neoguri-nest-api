package com.neoguri.neogurinest.api.application.board.action.post.usecase

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotActionableException

interface BoardPostBookmarkAddUseCase {

    @Throws(
        BoardPostNotFoundException::class,
        BoardPostStatusNotActionableException::class
    )
    fun execute(postId: String, actor: BoardActor): Boolean
}