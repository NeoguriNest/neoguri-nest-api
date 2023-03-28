package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentGetManyUsingCursorUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetMany
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.board.comment.BoardCommentSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils

@Service
class BoardCommentGetManyUsingCursor(
    private val repository: BoardCommentEntityRepositoryInterface
) : AbstractGetMany(), BoardCommentGetManyUsingCursorUseCase {

    override fun execute(
        filter: BoardCommentFilterDto,
        pagination: CursorPaginationDto,
        orders: List<OrderRequestDto>,
        actor: BoardActor?
    ): CursorPaginatedResultDto<BoardCommentDto> {

        val filterSpec = BoardCommentSpecification.of(filter)

        val sort: Sort = if (pagination.cursors.isEmpty()) {
            makeSort(orders)
        } else {
            Sort.by(pagination.cursors.map { it.order }.toMutableList())
        }

        // Default
        if (sort.isEmpty) {
            Sort.by(Sort.Order(Sort.Direction.DESC, "createdAt"))
        }

        val boardPosts = repository.findBySpecificationUsingCursorPagination(
            CursorPageRequest(pagination.cursors, filterSpec, PageRequest.of(0, pagination.size, sort))
        )

        return CursorPaginatedResultDto<BoardCommentDto>(
            Base64Utils.encodeToUrlSafeString(boardPosts.cursors.joinToString(",").toByteArray()),
            boardPosts.list.map { BoardCommentDto.of(it) },
            boardPosts.total
        )
    }

}