package com.neoguri.neogurinest.api.application.board.channel.dto

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class BoardChannelDto(
    val channelId: String,
    val nestId: Int?,
    val title: String,
    val status: DescribedEnumDto<BoardStatus>,
    val createdAt: String,
    val lastUploadedAt: String?
) {
    
    companion object {
        fun of(entity: BoardChannel): BoardChannelDto {

            return BoardChannelDto(
                entity.id!!.toString(),
                entity.nestId,
                entity.title!!,
                DescribedEnumDto(
                    entity.status!!,
                    when(entity.status) {
                        BoardStatus.ACTIVATED -> "활성화됨"
                        BoardStatus.CREATED -> "생성됨"
                        BoardStatus.SUSPENDED -> "사용중지됨"
                        BoardStatus.DELETED -> "삭제됨"
                        else -> "-"
                    }
                ),
                entity.createdAt!!.toString(),
                entity.lastUploadedAt?.toString()
            )
        }
    }
}