package com.neoguri.neogurinest.api.application.common.dto

import org.springframework.data.domain.Page

data class PaginatedResultDto<T>(
    val page: Int,
    val size: Int,
    val list: List<T>,
    val total: Int,
    val totalPage: Int
) {

    companion object {
        fun <T> of(pagination: PaginationDto, list: List<T>, result: Page<*>): PaginatedResultDto<T> {
            return PaginatedResultDto (
                pagination.page + 1,
                pagination.size,
                list,
                result.totalElements.toInt(),
                result.totalPages
            )
        }
    }
}