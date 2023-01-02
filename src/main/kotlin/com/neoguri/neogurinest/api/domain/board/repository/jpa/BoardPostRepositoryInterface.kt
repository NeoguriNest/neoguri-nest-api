package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostRepositoryInterface : JpaRepository<BoardPost, String> {
}