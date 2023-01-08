package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import org.springframework.data.jpa.repository.JpaRepository

interface BoardChannelRepositoryInterface : JpaRepository<BoardChannel, String> {
}