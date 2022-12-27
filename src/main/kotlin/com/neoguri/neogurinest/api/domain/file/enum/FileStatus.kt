package com.neoguri.neogurinest.api.domain.file.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class FileStatus(val value: Int) {
    DELETED(-2),
    INVISIBLE(-1),
    VISIBLE(0);
}

@Converter(autoApply = true)
class FileStatusAttributeConverter : AttributeConverter<FileStatus, Int> {

    override fun convertToDatabaseColumn(attribute: FileStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): FileStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            FileStatus.values().first { it.value == dbData }
        }
    }
}