package com.neoguri.neogurinest.api.application.board.comment.dto

import com.neoguri.neogurinest.api.application.board.dto.BoardActorDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class BoardCommentDto(
    val id: String,
    val nestId: Int?,
    val postId: String,
    val content: String,
    val status: DescribedEnumDto<BoardCommentStatus>,
    val creator: BoardActorDto?,
    val createdAt: String,
    val updatedAt: String?
) {
    companion object {
        fun of(entity: BoardComment): BoardCommentDto {
            val user = entity.user
            return BoardCommentDto(
                entity.id!!,
                entity.nestId,
                entity.post!!.id!!,
                entity.content!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        BoardCommentStatus.CREATED -> "생성됨"
                        BoardCommentStatus.BLOCKED -> "차단됨"
                        BoardCommentStatus.DELETED -> "삭제됨"
                        else -> "-"
                    }
                ),
                if (user != null) {
                    BoardActorDto(user.id!!, user.nickname!!)
                } else {
                    null
                },
                entity.createdAt!!.toString(),
                entity.updatedAt?.toString()
            )
        }
    }
}