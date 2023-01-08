package com.neoguri.neogurinest.api.application.board.post.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus
import com.neoguri.neogurinest.api.persistence.specification.RangeInstant

data class BoardPostFilterDto(
    val boardId: String,
    val status: BoardPostStatus?,
    val title: String?,
    val content: String?,
    val createdAt: RangeInstant?,
    val updatedAt: RangeInstant?
) {}