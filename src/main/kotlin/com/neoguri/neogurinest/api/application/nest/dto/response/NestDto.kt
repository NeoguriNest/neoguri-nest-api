package com.neoguri.neogurinest.api.application.nest.dto.response

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.nest.entity.Nest
import com.neoguri.neogurinest.api.domain.nest.enum.NestStatus
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class NestDto(
    val nestId: Int,
    val title: String,
    val city: String,
    val district: String,
    val status: DescribedEnumDto<NestStatus>,
    val createdAt: String,
    val lastUpdatedAt: String?
) {

    companion object {
        fun of(entity: Nest): NestDto {

            return NestDto(
                entity.id!!,
                entity.title!!,
                entity.city!!,
                entity.district!!,
                DescribedEnumDto(
                    entity.status!!,
                    when (entity.status) {
                        NestStatus.ACTIVATED -> "승인"
                        NestStatus.DELETED -> "삭제"
                        NestStatus.CREATED -> "승인대기"
                        NestStatus.SUSPENDED -> "일시차단"
                        else -> "-"
                    }
                ),
                entity.createdAt!!.toString(),
                entity.lastUploadedAt?.toString()
            )

        }

    }

}
