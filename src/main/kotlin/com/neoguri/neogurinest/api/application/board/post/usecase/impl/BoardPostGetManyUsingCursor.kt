package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetManyUsingCursorUseCase
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetManyUsingCursor
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.board.post.BoardPostSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.stereotype.Service

@Service
class BoardPostGetManyUsingCursor(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : AbstractGetManyUsingCursor(), BoardPostGetManyUsingCursorUseCase {

    override fun execute(
        filter: BoardPostFilterDto,
        pagination: CursorPaginationDto,
        orders: List<OrderRequestDto>,
        actor: BoardActor?)
    : CursorPaginatedResultDto<BoardPostDto> {

        val filterSpec = BoardPostSpecification.of(filter)

        val sort: Sort = makeSort(orders, pagination.cursors, Sort.by(Order(Sort.Direction.DESC, "createdAt")))

        val boardPosts = boardPostRepository.findBySpecificationUsingCursor(
            CursorPageRequest(pagination.cursors, filterSpec, PageRequest.of(0, pagination.size, sort))
        )

        return CursorPaginatedResultDto(
            buildCursorString(boardPosts.cursors),
            boardPosts.list.map { BoardPostDto.of(it) },
            boardPosts.total
        )
    }

}