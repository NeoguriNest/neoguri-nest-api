package com.neoguri.neogurinest.api.domain.nest.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class NestStatus(val value: Int) {
    CREATED(0),
    ACTIVATED(1),
    SUSPENDED(-1),
    DELETED(-2)
}

@Converter(autoApply = true)
class NestStatusAttributeConverter : AttributeConverter<NestStatus, Int> {

    override fun convertToDatabaseColumn(attribute: NestStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): NestStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            NestStatus.values().first { it.value == dbData }
        }
    }
}