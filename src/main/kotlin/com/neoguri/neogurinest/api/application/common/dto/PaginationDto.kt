package com.neoguri.neogurinest.api.application.common.dto

import com.neoguri.neogurinest.api.presentation.param.OrderDtoBuilder

data class PaginationDto(
    val page: Int,
    val orders: List<OrderRequestDto>,
    val size: Int
) {

    companion object {
        fun of(requestDto: PaginationRequestDto): PaginationDto {
            val orders = OrderDtoBuilder(requestDto.order).build()
            return PaginationDto(requestDto.page - 1, orders, requestDto.size)
        }
    }

}