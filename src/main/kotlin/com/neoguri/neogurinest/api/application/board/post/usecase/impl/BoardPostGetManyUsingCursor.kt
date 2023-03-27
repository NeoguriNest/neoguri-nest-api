package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetManyUsingCursorUseCase
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginatedResultDto
import com.neoguri.neogurinest.api.application.common.dto.CursorPaginationDto
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.board.post.BoardPostSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Base64Utils

@Service
class BoardPostGetManyUsingCursor(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostGetManyUsingCursorUseCase {

    @Retryable(maxAttempts = 3)
    @Transactional
    override fun execute(filter: BoardPostFilterDto, pagination: CursorPaginationDto, actor: BoardActor?): CursorPaginatedResultDto<BoardPostDto> {

        val filterSpec = BoardPostSpecification.of(filter)

        val orders: MutableList<Order> = pagination.cursors.map { it.order }.toMutableList()

        // Default
        if (orders.isEmpty()) {
            orders.add(Order(Sort.Direction.DESC, "createdAt"))
        }

        val boardPosts = boardPostRepository.findBySpecificationUsingCursorPagination(
            CursorPageRequest(pagination.cursors, filterSpec, PageRequest.of(0, pagination.size, Sort.by(orders)))
        )

        return CursorPaginatedResultDto<BoardPostDto>(
            Base64Utils.encodeToUrlSafeString(boardPosts.cursors.joinToString(",").toByteArray()),
            boardPosts.list.map { BoardPostDto.of(it) },
            boardPosts.total
        )
    }

}