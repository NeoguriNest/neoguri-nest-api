package com.neoguri.neogurinest.api.domain.board.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class BoardPostStatus(val value: Int) {
    DELETED(-2),
    BLOCKED(-1),
    CREATED(0);

    fun isCommentable(): Boolean {
        return (arrayListOf(CREATED).firstOrNull { it.value == this.value } != null)
    }

    fun isActionable(): Boolean {
        return (arrayListOf(CREATED).firstOrNull { it.value == this.value } != null)
    }
}

@Converter(autoApply = true)
class BoardPostStatusAttributeConverter : AttributeConverter<BoardPostStatus, Int> {

    override fun convertToDatabaseColumn(attribute: BoardPostStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): BoardPostStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            BoardPostStatus.values().first { it.value == dbData }
        }
    }
}