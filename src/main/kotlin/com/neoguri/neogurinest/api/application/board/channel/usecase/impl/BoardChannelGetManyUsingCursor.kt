package com.neoguri.neogurinest.api.application.board.channel.usecase.impl

import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelDto
import com.neoguri.neogurinest.api.application.board.channel.dto.BoardChannelFilterDto
import com.neoguri.neogurinest.api.application.board.channel.usecase.BoardChannelGetManyUsingCursorUseCase
import com.neoguri.neogurinest.api.application.board.usecase.AbstractGetManyUsingCursor
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.application.common.dto.OrderRequestDto
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.board.channel.BoardChannelSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardChannelGetManyUsingCursor(
    private val repository: BoardChannelEntityRepositoryInterface
) : AbstractGetManyUsingCursor(), BoardChannelGetManyUsingCursorUseCase {

    override fun execute(
        filter: BoardChannelFilterDto,
        pagination: CursorPaginationDto,
        orders: List<OrderRequestDto>,
        actor: BoardActor?
    ): CursorPaginatedResultDto<BoardChannelDto> {

        val filterSpec = BoardChannelSpecification.of(filter)

        val sort: Sort = makeSort(orders, pagination.cursors, Sort.by(Sort.Order(Sort.Direction.DESC, "createdAt")))

        val boardPosts = repository.findBySpecificationUsingCursor(
            CursorPageRequest(pagination.cursors, filterSpec, PageRequest.of(0, pagination.size, sort))
        )

        return CursorPaginatedResultDto(
            buildCursorString(boardPosts.cursors),
            boardPosts.list.map { BoardChannelDto.of(it) },
            boardPosts.total
        )
    }

}