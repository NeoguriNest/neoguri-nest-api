package com.neoguri.neogurinest.api.domain.common

import com.neoguri.neogurinest.api.domain.common.exception.CursorBuildFailedException
import com.neoguri.neogurinest.api.domain.common.type.StringToTypeConverter
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class CursorStringParser(val converter: StringToTypeConverter) {

    private val cursorOrderIndex = 0
    private val cursorValueIndex = 1

    private val fieldNameIndex = 0
    private val fieldDirectionIndex = 1

    fun parse(plainCursorText: String?): List<Cursor> {
        if (plainCursorText.isNullOrBlank()) {
            return emptyList()
        }

        return plainCursorText.split(",").map {
            val cursor = it.split("=")

            val orderString = cursor[cursorOrderIndex]
            val value = converter.convert(cursor[cursorValueIndex])

            val field = orderString.split(" ")
            val fieldName = field[fieldNameIndex]
            val fieldDirection = field[fieldDirectionIndex]

            try {
                Cursor(Sort.Order(Sort.Direction.fromString(fieldDirection), fieldName), value as Comparable<Any>)
            } catch (e: Exception) {
                throw CursorBuildFailedException(e.message)
            }
        }
    }
}
