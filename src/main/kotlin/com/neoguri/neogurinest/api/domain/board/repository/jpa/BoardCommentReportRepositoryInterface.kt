package com.neoguri.neogurinest.api.domain.board.repository.jpa

import com.neoguri.neogurinest.api.domain.board.entity.*
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCommentReportRepositoryInterface : JpaRepository<BoardCommentReport, String> {

}