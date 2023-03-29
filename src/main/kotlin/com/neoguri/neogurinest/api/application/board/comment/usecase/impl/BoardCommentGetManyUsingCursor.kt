package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentGetManyUsingCursorUseCase
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetManyUsingCursor
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.board.comment.BoardCommentSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardCommentGetManyUsingCursor(
    private val repository: BoardCommentEntityRepositoryInterface
) : AbstractGetManyUsingCursor(), BoardCommentGetManyUsingCursorUseCase {

    override fun execute(
        filter: BoardCommentFilterDto,
        pagination: CursorPaginationDto,
        orders: List<OrderRequestDto>,
        actor: BoardActor?
    ): CursorPaginatedResultDto<BoardCommentDto> {

        val filterSpec = BoardCommentSpecification.of(filter)

        val sort: Sort = makeSort(orders, pagination.cursors, Sort.by(Sort.Order(Sort.Direction.DESC, "createdAt")))

        val comments = repository.findBySpecificationUsingCursor(
            CursorPageRequest(pagination.cursors, filterSpec, PageRequest.of(0, pagination.size, sort))
        )

        return CursorPaginatedResultDto(
            buildCursorString(comments.cursors),
            comments.list.map { BoardCommentDto.of(it) },
            comments.total
        )
    }

}