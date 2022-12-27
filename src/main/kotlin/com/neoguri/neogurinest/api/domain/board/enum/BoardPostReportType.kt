package com.neoguri.neogurinest.api.domain.board.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class BoardPostReportType(val value: Int) {
    BAD_WORD(1), // 욕설 및 비방
    SENSITIVE(2), // 민감한 주제
    COPYRIGHT(3), // 저작권 위반
    DECEIVE(4), // 사기, 거짓 정보
    HATES(5), // 혐오 발언
    BULLY(6), // 따돌림, 괴롭힘
    VIOLATION(7), // 폭력
    SPAM(8), // 스팸
    SEXUAL(9), // 음란물
    DONT_LIKE(10), // 마음에 들지 않음
    SUICIDE(11),
    ETC(12)
}

@Converter(autoApply = true)
class BoardPostReportTypeAttributeConverter : AttributeConverter<BoardPostReportType, Int> {

    override fun convertToDatabaseColumn(attribute: BoardPostReportType?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): BoardPostReportType {
        return if (dbData === null) {
            throw UnexpectedStatusException()
        } else {
            BoardPostReportType.values().first { it.value == dbData }
        }
    }
}
