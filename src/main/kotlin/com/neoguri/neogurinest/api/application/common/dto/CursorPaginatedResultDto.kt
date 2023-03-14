package com.neoguri.neogurinest.api.application.common.dto

data class CursorPaginatedResultDto<T>(
    val cursor: String?,
    val list: List<T>,
    val total: Int
) {
}