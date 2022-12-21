package com.neoguri.neogurinest.api.domain.board.enum

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