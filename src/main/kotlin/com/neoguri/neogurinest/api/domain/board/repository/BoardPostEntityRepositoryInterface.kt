package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardPostEntityRepositoryInterface: AggregateRootRepository<BoardPost, String> {

    fun findByIdOrFail(id: String): BoardPost
}