package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BoardChannelRepositoryInterface : JpaRepository<BoardChannel, String>, JpaSpecificationExecutor<BoardChannel> {
}