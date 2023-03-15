package com.neoguri.neogurinest.api.presentation.param

import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto

class OrderDtoBuilder(
    private val orderString: String?
) {

    fun build(): List<OrderRequestDto> {
        if (orderString.isNullOrEmpty()) {
            return emptyList()
        }

        return orderString.split("|")
            .filter { it.isNotBlank() }
            .map { primitiveOrder ->
                val order = primitiveOrder.split(" ")
                val field = order[0]
                val direction = if (order.size > 1) order[1] else ""

                OrderRequestDto(field, direction == "desc")
            }
    }
}