package com.neoguri.neogurinest.api.domain.user.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class UserFileType(val value: String) {
    PROFILE("profile"),
}

@Converter(autoApply = true)
class UserFileTypeAttributeConverter : AttributeConverter<UserFileType, String> {

    override fun convertToDatabaseColumn(attribute: UserFileType?): String {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: String?): UserFileType {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            UserFileType.values().first { it.value === dbData }
        }
    }
}