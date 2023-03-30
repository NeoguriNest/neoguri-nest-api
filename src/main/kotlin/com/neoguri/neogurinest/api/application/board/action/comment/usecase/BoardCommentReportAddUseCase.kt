package com.neoguri.neogurinest.api.application.board.action.comment.usecase

import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportAddDto
import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor

interface BoardCommentReportAddUseCase {

    fun execute(addDto: BoardCommentReportAddDto, actor: BoardActor): BoardCommentReportDto
}