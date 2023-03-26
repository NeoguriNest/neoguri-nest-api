package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BoardCommentEntityRepositoryInterface : AggregateRootRepository<BoardComment, String> {

    fun findByIdOrFail(id: String): BoardComment

    fun findBySpecification(
        specification: Specification<BoardComment>,
        order: Sort.Order,
        limit: Int
    ): List<BoardComment>

    fun countBySpecification(specification: Specification<BoardComment>?): Int

    fun findBySpecificationUsingCursorPagination(
        cursorRequest: CursorPageRequest<BoardComment>
    ): CursorPage<BoardComment>

}