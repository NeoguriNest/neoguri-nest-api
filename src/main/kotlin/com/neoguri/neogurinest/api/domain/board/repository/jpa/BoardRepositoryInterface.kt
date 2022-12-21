package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepositoryInterface : JpaRepository<Board, String> {
}