package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostReport
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostReportRepositoryInterface : JpaRepository<BoardPostReport, String> {

}