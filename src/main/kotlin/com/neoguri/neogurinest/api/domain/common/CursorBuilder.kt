package com.neoguri.neogurinest.api.domain.common

class CursorBuilder(val cursors: List<Cursor>) {

    val fields: List<String> = cursors.map { it.order.property }

    inline fun <reified T> fromEntity(entity: T): List<Cursor> {

        return cursors.map { cursor ->
            val newValue = T::class.java.declaredFields.first {
                it.isAccessible = true
                it.name == cursor.order.property
            }.get(entity)

            Cursor(cursor.order, newValue as Comparable<Any>)
        }
    }
}
