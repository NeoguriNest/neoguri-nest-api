package com.neoguri.neogurinest.api.application.board.usecase

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto

interface GetManyUsingCursorUseCase<F, T> {

    fun execute(
        filter: F,
        pagination: CursorPaginationDto,
        orders: List<OrderRequestDto>,
        actor: BoardActor?
    ): CursorPaginatedResultDto<T>
}