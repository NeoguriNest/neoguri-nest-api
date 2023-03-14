package com.neoguri.neogurinest.api.application.common.dto

import com.neoguri.neogurinest.api.domain.common.Cursor

data class CursorPaginationDto(val cursors: List<Cursor>, var size: Int = 50) {

    private val maximumSize = 50

    init {
        this.size = maximumSize.coerceAtMost(size)
    }
}
