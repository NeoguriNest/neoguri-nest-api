package com.neoguri.neogurinest.api.application.board.channel.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus

data class BoardStatusUpdateDto(
    val boardId: String,
    val status: BoardStatus
) {}