package com.neoguri.neogurinest.api.application.aspect

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor

class BoardContext {

    var actor: BoardActor? = null

    fun isAnonymous(): Boolean {
        return actor == null
    }

    companion object {
        private val context: ThreadLocal<BoardContext> = ThreadLocal()

        fun getInstance(): BoardContext {
            if (context.get() == null) {
                context.set(BoardContext())
            }
            return context.get()
        }

        fun clear() {
            context.remove()
        }
    }
}