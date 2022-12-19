package com.neoguri.neogurinest.api.application.nest.dto.response

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.nest.enum.NestStatus
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class NestDto(
    val nestId: Int,
    val ttile: String,
    val city: String,
    val district: String,
    val status: DescribedEnumDto<NestStatus>,
    val createdAt: String,
    val lastUpdatedAt: String?
) {

    companion object {
        fun of(entity: Nest): NestDto {

            val formatText = "yyyy-MM-dd hh:mm:ss"

            return NestDto(
                entity.id!!,
                entity.title!!,
                entity.city!!,
                entity.district!!,
                DescribedEnumDto<NestStatus>(
                    entity.status!!,
                    when (entity.status) {
                        NestStatus.ACTIVATED -> "승인"
                        NestStatus.DELETED -> "삭제"
                        NestStatus.CREATED -> "승인대기"
                        NestStatus.SUSPENDED -> "일시차단"
                        else -> "비공개"
                    }
                ),
                DateFormatUtil.format(formatText, entity.createdAt!!),
                if (entity.lastUploadedAt === null) null else DateFormatUtil.format(formatText, entity.lastUploadedAt!!)
            )

        }

    }

}
