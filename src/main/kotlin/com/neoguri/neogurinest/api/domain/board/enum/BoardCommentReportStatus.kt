package com.neoguri.neogurinest.api.domain.board.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class BoardCommentReportStatus(val value: Int) {
    IGNORED(-1),
    CREATED(0),
    CHECKED(1),
    PROCESSED(2)
}

@Converter(autoApply = true)
class BoardCommentReportStatusAttributeConverter : AttributeConverter<BoardCommentReportStatus, Int> {

    override fun convertToDatabaseColumn(attribute: BoardCommentReportStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): BoardCommentReportStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            BoardCommentReportStatus.values().first { it.value == dbData }
        }
    }
}
