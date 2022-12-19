package com.neoguri.neogurinest.api.application.board.dto.request

import com.neoguri.neogurinest.api.domain.board.enum.BoardStatus

data class BoardAddDto(
   val nestId: Int?,
   val title: String
) {}