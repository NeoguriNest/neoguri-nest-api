package com.neoguri.neogurinest.api.application.board.post.usecase

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto

interface BoardPostGetManyUsingCursorUseCaseInterface {

    fun execute(filter: BoardPostFilterDto, pagination: CursorPaginationDto, actor: BoardActor?): CursorPaginatedResultDto<BoardPostDto>
}