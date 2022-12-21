package com.neoguri.neogurinest.api.application.board.dto.response

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.Board
import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class BoardDto(
   val boardId: String,
   val nestId: Int?,
   val title: String,
   val status: DescribedEnumDto<BoardStatus>,
   val createdAt: String,
   val lastUploadedAt: String?
) {
    
    companion object {
        fun of(entity: Board): BoardDto {
            val formatText = "yyyy-MM-dd HH:mm:ss"

            return BoardDto(
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
                DateFormatUtil.format(formatText, entity.createdAt!!),
                if (entity.lastUploadedAt === null) null else DateFormatUtil.format(formatText, entity.lastUploadedAt!!)
            )
        }
    }
}