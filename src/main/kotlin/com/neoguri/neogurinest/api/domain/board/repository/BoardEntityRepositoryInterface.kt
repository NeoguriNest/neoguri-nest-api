package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.Board
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardEntityRepositoryInterface: AggregateRootRepository<Board, String> {

    fun findByIdOrFail(id: String): Board
}