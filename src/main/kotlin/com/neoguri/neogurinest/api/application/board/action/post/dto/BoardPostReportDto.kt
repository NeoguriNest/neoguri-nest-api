package com.neoguri.neogurinest.api.application.board.action.post.dto

import com.neoguri.neogurinest.api.application.board.dto.BoardActorDto
import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostReport
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportStatus
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportType

data class BoardPostReportDto(
    val reportId: String,
    val postId: String,
    val type: DescribedEnumDto<BoardPostReportType>,
    val content: String,
    val reportedContent: String,
    val status: DescribedEnumDto<BoardPostReportStatus>,
    val creatorId: BoardActorDto,
    val createdAt: String,
    val updatedAt: String?,
) {

    companion object {
        fun of(entity: BoardPostReport): BoardPostReportDto {

            return BoardPostReportDto(
                entity.id!!,
                entity.post!!.id!!,
                DescribedEnumDto(
                    entity.type!!,
                    when (entity.type) {
                        BoardPostReportType.BAD_WORD ->  "욕설 및 비방"
                        BoardPostReportType.SENSITIVE ->  "민감한 주제"
                        BoardPostReportType.COPYRIGHT ->  "저작권 위반"
                        BoardPostReportType.DECEIVE ->  "사기 ->  거짓 정보"
                        BoardPostReportType.HATES ->  "혐오 발언"
                        BoardPostReportType.BULLY ->  "따돌림 ->  괴롭힘"
                        BoardPostReportType.VIOLATION ->  "폭력"
                        BoardPostReportType.SPAM ->  "스팸"
                        BoardPostReportType.SEXUAL ->  "음란물"
                        BoardPostReportType.DONT_LIKE ->  "마음에 들지 않음"
                        BoardPostReportType.SUICIDE -> "자살 방조"
                        BoardPostReportType.ETC -> "기타"
                        else -> "-"
                    }
                ),
                entity.content!!,
                entity.reportedContent!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        BoardPostReportStatus.IGNORED ->  "기각됨"
                        BoardPostReportStatus.CREATED ->  "생성됨"
                        BoardPostReportStatus.CHECKED ->  "확인됨"
                        BoardPostReportStatus.PROCESSED ->  "처리완료"
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