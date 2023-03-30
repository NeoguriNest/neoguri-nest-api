package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentLike
import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentLikeId
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCommentLikeRepositoryInterface : JpaRepository<BoardCommentLike, BoardCommentLikeId> {

}