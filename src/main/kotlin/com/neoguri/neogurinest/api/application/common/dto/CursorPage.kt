package com.neoguri.neogurinest.api.application.common.dto

import com.neoguri.neogurinest.api.domain.common.Cursor

data class CursorPage<T>(
    val cursors: List<Cursor>,
    val list: List<T>,
    val total: Int
) {
    companion object {
        fun <T> emptyPage(total: Int): CursorPage<T> {
            return CursorPage(emptyList(), emptyList(), total)
        }
    }
}