package com.neoguri.neogurinest.api.application.board.post.service

import com.neoguri.neogurinest.api.domain.board.entity.Board

interface BoardServiceInterface {
    fun uploadPost(board: Board): Board

}