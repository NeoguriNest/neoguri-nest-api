package com.neoguri.neogurinest.api.domain.board.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class BoardCommentStatus(val value: Int) {
    DELETED(-2),
    BLOCKED(-1),
    CREATED(0)
}

@Converter(autoApply = true)
class BoardCommentStatusAttributeConverter : AttributeConverter<BoardCommentStatus, Int> {

    override fun convertToDatabaseColumn(attribute: BoardCommentStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): BoardCommentStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            BoardCommentStatus.values().first { it.value == dbData }
        }
    }
}