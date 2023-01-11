package com.neoguri.neogurinest.api.application.board.post.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus

data class BoardPostStatusUpdateDto(
    val postId: String,
    val status: BoardPostStatus
) {}