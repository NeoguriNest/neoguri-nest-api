package com.neoguri.neogurinest.api.application.board.post.dto

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardHashtagDto
import com.neoguri.neogurinest.api.application.board.dto.BoardActorDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus

data class BoardPostDto(
    val postId: String,
    val nestId: Int?,
    val channelId: String,
    val title: String,
    val content: String,
    val status: DescribedEnumDto<BoardPostStatus>,
    val hashTags: List<BoardHashtagDto>,
    val creator: BoardActorDto?,
    val createdAt: String,
    val updatedAt: String?
) {

    companion object {
        fun of(entity: BoardPost): BoardPostDto {
            return BoardPostDto(
                entity.id!!.toString(),
                entity.nestId,
                entity.channel!!.id!!,
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
                entity.userId?.let { BoardActorDto(entity.userId!!, "-") },
                entity.createdAt!!.toString(),
                entity.updatedAt?.toString()
            )
        }

    }
}