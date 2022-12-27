package com.neoguri.neogurinest.api.domain.user.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class TermAndAgreementStatus(val value: Int) {
    DELETED(-2),
    INVISIBLE(-1),
    VISIBLE(0)
}

@Converter(autoApply = true)
class TermAndAgreementStatusAttributeConverter : AttributeConverter<TermAndAgreementStatus, Int> {

    override fun convertToDatabaseColumn(attribute: TermAndAgreementStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): TermAndAgreementStatus {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            TermAndAgreementStatus.values().first { it.value == dbData }
        }
    }
}