package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentReport
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface BoardCommentReportEntityRepositoryInterface: AggregateRootRepository<BoardCommentReport, String> {
    fun findByIdOrFail(id: String): BoardCommentReport
}