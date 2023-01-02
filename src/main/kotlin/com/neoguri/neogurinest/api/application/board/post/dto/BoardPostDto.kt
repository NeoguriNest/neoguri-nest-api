package com.neoguri.neogurinest.api.application.board.post.dto

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardHashtagDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus

data class BoardPostDto(
    val postId: String,
    val nestId: Int?,
    val boardId: String,
    val title: String,
    val content: String,
    val status: DescribedEnumDto<BoardPostStatus>,
    val hashTags: List<BoardHashtagDto>,
    val creator: BoardPostActorDto?
) {

    companion object {
        fun of(entity: BoardPost): BoardPostDto {

            return BoardPostDto(
                entity.id!!.toString(),
                entity.nestId,
                entity.boardId!!,
                entity.title!!,
                entity.content!!,
                DescribedEnumDto<BoardPostStatus>(
                    entity.status!!,
                    when (entity.status) {
                        BoardPostStatus.CREATED -> "생성됨"
                        BoardPostStatus.BLOCKED -> "차단됨"
                        BoardPostStatus.DELETED -> "삭제됨"
                        else -> "-"
                    }
                ),
                entity.hashTags.map { BoardHashtagDto.of(it.hashTag!!) },
                if (entity.userId != null) BoardPostActorDto(entity.userId!!) else null

            )
        }

    }
}