package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLike
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLikeId
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostLikeRepositoryInterface : JpaRepository<BoardPostLike, BoardPostLikeId> {

}