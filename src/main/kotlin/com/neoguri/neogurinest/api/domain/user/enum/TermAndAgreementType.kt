package com.neoguri.neogurinest.api.domain.user.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class TermAndAgreementType(val value: String) {
    DELETED("term"),
    AGREEMENT("agreement")
}

@Converter(autoApply = true)
class TermAndAgreementTypeAttributeConverter : AttributeConverter<TermAndAgreementType, String> {

    override fun convertToDatabaseColumn(attribute: TermAndAgreementType?): String {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: String?): TermAndAgreementType {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            TermAndAgreementType.values().first { it.value == dbData }
        }
    }
}