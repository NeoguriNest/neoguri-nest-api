package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BoardCommentRepositoryInterface : JpaRepository<BoardComment, String>, JpaSpecificationExecutor<BoardComment> {
}