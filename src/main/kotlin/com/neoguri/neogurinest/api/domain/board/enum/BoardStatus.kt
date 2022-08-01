package com.neoguri.neogurinest.api.domain.board.enum

enum class BoardStatus(val value: Int) {
    CREATED(0),
    ACTIVATED(1),
    SUSPENDED(-1),
    DELETED(-2)
}