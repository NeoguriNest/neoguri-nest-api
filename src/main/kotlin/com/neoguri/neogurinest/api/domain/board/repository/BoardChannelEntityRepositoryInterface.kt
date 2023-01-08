package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardChannelEntityRepositoryInterface: AggregateRootRepository<BoardChannel, String> {

    fun findByIdOrFail(id: String): BoardChannel
}