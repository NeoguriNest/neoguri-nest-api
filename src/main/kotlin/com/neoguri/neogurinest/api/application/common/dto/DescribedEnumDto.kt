package com.neoguri.neogurinest.api.application.common.dto

import com.neoguri.neogurinest.api.domain.user.enum.Gender

data class DescribedEnumDto(
    val value: Gender,
    val description: String
) {
}