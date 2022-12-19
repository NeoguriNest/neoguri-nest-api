package com.neoguri.neogurinest.api.application.board.dto.response

import com.neoguri.neogurinest.api.application.board.dto.request.BoardPostAddDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus

data class BoardPostDto(
    val postId: String,
    val nestId: Int?,
    val boardId: String,
    val userId: String,
    val title: String,
    val content: String,
    val status: DescribedEnumDto<BoardPostStatus>,
    val hashTags: List<>,

) {

    companion object {
        fun of(entity: BoardPost): BoardPostDto {

            return BoardPost(
                entity.id,
                entity.nestId,
                entity.boardId,
                entity.userId,
                entity.title,
                entity.content,
                DescribedEnumDto<BoardPostStatus>(
                    entity.status,
                    when (entity.status) {
                        BoardPostStatus.CREATED -> "생성됨",
                        BoardPostStatus.BLOCKED -> "차단됨",
                        BoardPostStatus.DELETED -> "삭제됨",
                        else -> "-"
                    }
                )
            )

        }

    }
}