package com.neoguri.neogurinest.api.application.board.usecase

import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto
import com.neoguri.neogurinest.api.domain.common.Cursor
import org.springframework.data.domain.Sort
import org.springframework.util.Base64Utils

abstract class AbstractGetManyUsingCursor : AbstractGetMany() {

    protected fun makeSort(orders: List<OrderRequestDto>, cursors: List<Cursor>, default: Sort): Sort {
        val sort = if (cursors.isEmpty()) {
            this.makeSort(orders)
        } else {
            Sort.by(cursors.map { it.order }.toMutableList())
        }

        return if (sort.isEmpty) {
            default
        } else {
            sort
        }
    }

    protected fun buildCursorString(cursors: List<Cursor>): String {
        return Base64Utils.encodeToUrlSafeString(cursors.joinToString(",").toByteArray())
    }

}