package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentGetManyUsingPaginationUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetMany
import com.neoguri.neogurinest.api.application.common.dto.PaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.PaginationDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.persistence.specification.board.comment.BoardCommentSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BoardCommentGetManyUsingPagination(
    private val repository: BoardCommentEntityRepositoryInterface
) : AbstractGetMany(), BoardCommentGetManyUsingPaginationUseCase {

    override fun execute(
        filter: BoardCommentFilterDto,
        pagination: PaginationDto,
        actor: BoardActor?
    ): PaginatedResultDto<BoardCommentDto> {

        val spec = BoardCommentSpecification.of(filter)

        val sort = makeSort(pagination.orders)
        val page = PageRequest.of(pagination.page, pagination.size, sort)
        val result = repository.findBySpecificationUsingPagination(spec, page)

        val list = result.content.map { e: BoardComment ->
            BoardCommentDto.of(e)
        }
        return PaginatedResultDto.of(pagination, list, result)
    }

}