package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLike
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLikeId
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardPostLikeEntityRepositoryInterface: AggregateRootRepository<BoardPostLike, BoardPostLikeId> {

}