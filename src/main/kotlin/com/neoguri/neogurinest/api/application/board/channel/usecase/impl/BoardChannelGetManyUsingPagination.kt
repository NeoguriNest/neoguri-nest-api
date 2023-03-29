package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelGetManyUsingPaginationUseCase
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetMany
import com.neoguri.neogurinest.api.application.common.dto.PaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.PaginationDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.persistence.specification.board.channel.BoardChannelSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BoardChannelGetManyUsingPagination(
    val repository: BoardChannelEntityRepositoryInterface
) : AbstractGetMany(), BoardChannelGetManyUsingPaginationUseCase {

    override fun execute(
        filter: BoardChannelFilterDto,
        pagination: PaginationDto,
        actor: BoardActor?
    ): PaginatedResultDto<BoardChannelDto> {

        val filterSpec = BoardChannelSpecification.of(filter)

        val sort = makeSort(pagination.orders)
        val pageRequest = PageRequest.of(pagination.page, pagination.size, sort)
        val page = repository.findBySpecificationUsingPagination(filterSpec, pageRequest)

        return PaginatedResultDto.of(pagination, page.content.map { BoardChannelDto.of(it) }, page)
    }
}