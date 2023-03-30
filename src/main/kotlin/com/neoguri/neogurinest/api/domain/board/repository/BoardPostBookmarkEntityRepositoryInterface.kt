package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmark
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmarkId
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardPostBookmarkEntityRepositoryInterface: AggregateRootRepository<BoardPostBookmark, BoardPostBookmarkId> {

}