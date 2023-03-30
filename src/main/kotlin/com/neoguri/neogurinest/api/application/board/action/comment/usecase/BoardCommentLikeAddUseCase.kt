package com.neoguri.neogurinest.api.application.board.action.comment.usecase

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.*

interface BoardCommentLikeAddUseCase {

    @Throws(
        BoardCommentNotFoundException::class,
        BoardCommentActionAlreadyExistException::class,
        BoardCommentStatusNotActionableException::class
    )
    fun execute(commentId: String, actor: BoardActor): Boolean
}