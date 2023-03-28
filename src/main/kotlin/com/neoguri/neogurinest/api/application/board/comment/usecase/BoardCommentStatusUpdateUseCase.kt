package com.neoguri.neogurinest.api.application.board.comment.usecase

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus

interface BoardCommentStatusUpdateUseCase {

    fun execute(commentId: String, status: BoardCommentStatus, actor: BoardActor): Boolean
}