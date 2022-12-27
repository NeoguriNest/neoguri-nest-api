package com.neoguri.neogurinest.api.domain.board.enum

import com.neoguri.neogurinest.api.domain.common.exception.UnexpectedStatusException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class BoardStatus(val value: Int) {
    CREATED(0),
    ACTIVATED(1),
    SUSPENDED(-1),
    DELETED(-2);

    companion object {
        fun getConvertable(to: BoardStatus): List<BoardStatus> {
            return when (to) {
                ACTIVATED -> getActivatable()
                DELETED -> getDeletable()
                SUSPENDED -> getSuspendable()
                else -> emptyList()
            }
        }

        private fun getActivatable(): List<BoardStatus> {
            return arrayListOf(CREATED, SUSPENDED)
        }

        private fun getDeletable(): List<BoardStatus> {
            return arrayListOf(CREATED, SUSPENDED, ACTIVATED)
        }

        private fun getSuspendable(): List<BoardStatus> {
            return arrayListOf(CREATED, SUSPENDED, ACTIVATED)
        }
    }
}

@Converter(autoApply = true)
class BoardStatusTypeHandler : AttributeConverter<BoardStatus, Int> {
    override fun convertToDatabaseColumn(attribute: BoardStatus?): Int {
        return if (attribute === null) {
            throw UnexpectedStatusException()
        } else {
            attribute.value
        }
    }

    override fun convertToEntityAttribute(dbData: Int?): BoardStatus {
        return if (dbData === null){
            throw UnexpectedStatusException()
        } else {
            BoardStatus.values().first { it.value == dbData }
        }
    }

}
