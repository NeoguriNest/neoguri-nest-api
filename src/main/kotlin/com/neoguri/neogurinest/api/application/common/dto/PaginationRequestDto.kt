package com.neoguri.neogurinest.api.application.common.dto

data class PaginationRequestDto(
    val page: Int = 1,
    val order: String?,
    val size: Int = 50
) {
}