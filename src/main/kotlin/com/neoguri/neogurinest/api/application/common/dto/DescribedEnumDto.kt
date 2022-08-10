package com.neoguri.neogurinest.api.application.common.dto

data class DescribedEnumDto<T>(
    val value: T,
    val description: String
) {
}