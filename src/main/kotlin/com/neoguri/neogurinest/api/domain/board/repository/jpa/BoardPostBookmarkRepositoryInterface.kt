package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmark
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmarkId
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostBookmarkRepositoryInterface : JpaRepository<BoardPostBookmark, BoardPostBookmarkId> {

}