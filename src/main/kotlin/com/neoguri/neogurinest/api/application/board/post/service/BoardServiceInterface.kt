package com.neoguri.neogurinest.api.application.board.post.service

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel

interface BoardServiceInterface {
    fun uploadPost(board: BoardChannel): BoardChannel

}