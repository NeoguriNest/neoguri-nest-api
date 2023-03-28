package com.neoguri.neogurinest.api.application.board.usecase

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.application.common.dto.PaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.PaginationDto

interface GetManyUsingPaginationUseCase<F, T> {

    fun execute(
        filter: F,
        pagination: PaginationDto,
        actor: BoardActor?
    ): PaginatedResultDto<T>
}