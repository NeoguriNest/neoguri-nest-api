package com.neoguri.neogurinest.api.application.board.comment.usecase

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentAddDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor

interface BoardCommentAddUseCase {

    fun execute(addDto: BoardCommentAddDto, actor: BoardActor): BoardCommentDto
}