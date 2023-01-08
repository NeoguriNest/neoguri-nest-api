package com.neoguri.neogurinest.api.domain.user.enum

import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class Gender(val value: String) {
    MALE("M"),
    FEMALE("F"),
    NONE("X")
}

@Converter(autoApply = true)
class GenderAttributeConverter : AttributeConverter<Gender?, String> {

    override fun convertToDatabaseColumn(attribute: Gender?): String? {
        return if (attribute === null) {
            null
        } else {
            return attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Gender? {
        return if (dbData === null) {
            null
        } else {
            Gender.values().first { it.value == dbData }
        }
    }
}