package com.neoguri.neogurinest.api.application.board.action.post.usecase

import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportAddDto
import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor

interface BoardPostReportAddUseCase {

    fun execute(addDto: BoardPostReportAddDto, actor: BoardActor): BoardPostReportDto
}