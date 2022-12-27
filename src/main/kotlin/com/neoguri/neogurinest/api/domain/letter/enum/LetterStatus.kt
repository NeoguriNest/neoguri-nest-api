package com.neoguri.neogurinest.api.domain.letter.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class LetterStatus(val value: Int) {
    SENT(0),
    READ(1)
}

@Converter(autoApply = true)
class LetterStatusAttributeConverter : AttributeConverter<LetterStatus, Int> {

    override fun convertToDatabaseColumn(attribute: LetterStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): LetterStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            LetterStatus.values().first { it.value == dbData }
        }
    }
}