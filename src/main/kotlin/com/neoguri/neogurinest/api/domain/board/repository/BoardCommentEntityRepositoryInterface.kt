package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

interface BoardCommentEntityRepositoryInterface : AggregateRootRepository<BoardComment, String> {

    fun findByIdOrFail(id: String): BoardComment

    fun findBySpecification(
        specification: Specification<BoardComment>,
        order: Sort,
        limit: Int
    ): List<BoardComment>

    fun countBySpecification(specification: Specification<BoardComment>?): Int

    fun findBySpecificationUsingCursor(
        cursorRequest: CursorPageRequest<BoardComment>
    ): CursorPage<BoardComment>

    fun findBySpecificationUsingPagination(
        specification: Specification<BoardComment>?,
        pageRequest: PageRequest
    ): Page<BoardComment>

}