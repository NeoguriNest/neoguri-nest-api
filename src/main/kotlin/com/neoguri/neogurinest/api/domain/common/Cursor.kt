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

    companion object {

        private const val CURSOR_ORDER_IDX = 0
        private const val CURSOR_VALUE_IDX = 1

        private const val FIELD_NAME_IDX = 0
        private const val FIELD_DIRECTION_IDX = 1

        @Throws(CursorBuildFailedException::class)
        fun of(plainCursorText: String?): List<Cursor> {
            if (plainCursorText.isNullOrBlank()) {
                return emptyList()
            }

            val cursors = plainCursorText.split(",")
            return cursors.map {
                val cursor = it.split("=")

                val orderString = cursor[CURSOR_ORDER_IDX]
                val value = cursor[CURSOR_VALUE_IDX]

                val field = orderString.split(" ")
                val fieldName = field[FIELD_NAME_IDX]
                val fieldDirection = field[FIELD_DIRECTION_IDX]

                try {
                    Cursor(Order(Sort.Direction.fromString(fieldDirection), fieldName), value as Comparable<Any>)
                } catch(e: Exception) {
                    throw CursorBuildFailedException(e.message)
                }
            }
        }
    }
}
