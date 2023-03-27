package com.neoguri.neogurinest.api.application.board.comment.dto

import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.persistence.specification.RangeInstant

data class BoardCommentFilterDto(
    val nestId: String?,
    val channelId: String?,
    val postId: String?,
    val userId: String?,
    val parentId: String?,
    val status: List<BoardCommentStatus>?,
    val createdAt: RangeInstant?,
    val updatedAt: RangeInstant?
) {}