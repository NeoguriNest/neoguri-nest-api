package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetManyUsingPaginationUseCase
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetMany
import com.neoguri.neogurinest.api.application.common.dto.PaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.PaginationDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.persistence.specification.board.post.BoardPostSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BoardPostGetManyUsingPagination(
    val repository: BoardPostEntityRepositoryInterface
) : AbstractGetMany(), BoardPostGetManyUsingPaginationUseCase {


    override fun execute(filter: BoardPostFilterDto, pagination: PaginationDto, actor: BoardActor?): PaginatedResultDto<BoardPostDto> {

        val filterSpec = BoardPostSpecification.of(filter)

        val sort = makeSort(pagination.orders)
        val pageRequest = PageRequest.of(pagination.page, pagination.size, sort)
        val page = repository.findBySpecificationUsingPagination(filterSpec, pageRequest)

        return PaginatedResultDto.of(pagination, page.content.map { BoardPostDto.of(it) }, page)
    }

}