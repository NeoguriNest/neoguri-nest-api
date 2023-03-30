package com.neoguri.neogurinest.api.application.board.action.comment.dto

import com.neoguri.neogurinest.api.application.board.dto.BoardActorDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentReport
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentReportStatus
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentReportType

data class BoardCommentReportDto(
    val reportId: String,
    val commentId: String,
    val type: DescribedEnumDto<BoardCommentReportType>,
    val content: String,
    val reportedContent: String,
    val status: DescribedEnumDto<BoardCommentReportStatus>,
    val creatorId: BoardActorDto,
    val createdAt: String,
    val updatedAt: String?,
) {

    companion object {
        fun of(entity: BoardCommentReport): BoardCommentReportDto {

            return BoardCommentReportDto(
                entity.id!!,
                entity.comment!!.id!!,
                DescribedEnumDto(
                    entity.type!!,
                    when (entity.type) {
                        BoardCommentReportType.BAD_WORD ->  "욕설 및 비방"
                        BoardCommentReportType.SENSITIVE ->  "민감한 주제"
                        BoardCommentReportType.COPYRIGHT ->  "저작권 위반"
                        BoardCommentReportType.DECEIVE ->  "사기 ->  거짓 정보"
                        BoardCommentReportType.HATES ->  "혐오 발언"
                        BoardCommentReportType.BULLY ->  "따돌림 ->  괴롭힘"
                        BoardCommentReportType.VIOLATION ->  "폭력"
                        BoardCommentReportType.SPAM ->  "스팸"
                        BoardCommentReportType.SEXUAL ->  "음란물"
                        BoardCommentReportType.DONT_LIKE ->  "마음에 들지 않음"
                        BoardCommentReportType.SUICIDE -> "자살 방조"
                        BoardCommentReportType.ETC -> "기타"
                        else -> "-"
                    }
                ),
                entity.content!!,
                entity.reportedContent!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        BoardCommentReportStatus.IGNORED ->  "기각됨"
                        BoardCommentReportStatus.CREATED ->  "생성됨"
                        BoardCommentReportStatus.CHECKED ->  "확인됨"
                        BoardCommentReportStatus.PROCESSED ->  "처리완료"
                        else -> "-"
                    }
                ),
                entity.user!!.let { BoardActorDto(it.id!!, it.nickname) },
                entity.createdAt.toString(),
                entity.updatedAt?.toString(),
            )
        }
    }
}