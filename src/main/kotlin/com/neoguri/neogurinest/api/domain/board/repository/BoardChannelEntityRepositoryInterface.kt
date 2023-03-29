package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface BoardChannelEntityRepositoryInterface: AggregateRootRepository<BoardChannel, String> {

    @Throws(BoardChannelNotFoundException::class)
    fun findByIdOrFail(id: String): BoardChannel

    fun findBySpecification(specification: Specification<BoardChannel>?, limit: Int?): List<BoardChannel>

    fun findBySpecificationUsingCursor(
        cursorRequest: CursorPageRequest<BoardChannel>
    ): CursorPage<BoardChannel>

    fun findBySpecificationUsingPagination(
        specification: Specification<BoardChannel>?,
        page: Pageable
     ): Page<BoardChannel>

}