package com.neoguri.neogurinest.api.application.board.action.comment.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentReportType

data class BoardCommentReportAddDto(
    val commentId: String,
    val type: BoardCommentReportType,
    val content: String
) {
}