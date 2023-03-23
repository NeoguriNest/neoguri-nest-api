package com.neoguri.neogurinest.api.domain.common

import com.neoguri.neogurinest.api.domain.common.exception.CursorBuildFailedException
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order

data class Cursor(
    val order: Order,
    val value: Comparable<Any>
) {
    override fun toString(): String {
        return order.property + " " + order.direction.toString().lowercase() + "=" + value
    }

}
