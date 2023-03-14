package com.neoguri.neogurinest.api.application.common.dto

data class CursorPaginationRequestDto(
    val cursor: String?,
    val order: String?,
    val size: Int = 50
) {}
