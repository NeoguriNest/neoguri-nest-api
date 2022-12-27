package com.neoguri.neogurinest.api.domain.user.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class UserStatus(val value: Int) {
    LEFT(-2),
    BLOCK(-1),
    ACTIVATED(0)
}

@Converter(autoApply = true)
class UserStatusAttributeConverter : AttributeConverter<UserStatus, Int> {

    override fun convertToDatabaseColumn(attribute: UserStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): UserStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            UserStatus.values().first { it.value == dbData }
        }
    }
}