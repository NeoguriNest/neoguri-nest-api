package com.neoguri.neogurinest.api.application.common.dto

import com.neoguri.neogurinest.api.domain.common.Cursor

data class CursorPage<T>(
    val cursors: List<Cursor>,
    val list: List<T>,
    val total: Int
) {
    companion object {
        fun <T> empty(): CursorPage<T> {
            return CursorPage(emptyList(), emptyList(), 0)
        }
    }
}