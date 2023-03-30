package com.neoguri.neogurinest.api.application.board.action.post.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportType

data class BoardPostReportAddDto(
    val postId: String,
    val type: BoardPostReportType,
    val content: String
) {
}