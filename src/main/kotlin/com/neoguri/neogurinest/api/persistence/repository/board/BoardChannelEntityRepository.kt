package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardChannelRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.Cursor
import com.neoguri.neogurinest.api.domain.common.CursorBuilder
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.repository.AbstractCursorPaginationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class BoardChannelEntityRepository(
    val boardRepository: BoardChannelRepositoryInterface
) : AbstractCursorPaginationRepository<BoardChannel>(), BoardChannelEntityRepositoryInterface {

    override fun save(entity: BoardChannel): BoardChannel {
        return boardRepository.save(entity)
    }

    override fun findById(id: String): BoardChannel? {
        return boardRepository.findById(id).orElse(null)
    }

    override fun countBySpecification(specification: Specification<BoardChannel>?): Int {
        return boardRepository.count(specification).toInt()
    }

    override fun findByIdOrFail(id: String): BoardChannel {
        return findById(id) ?: throw BoardChannelNotFoundException()
    }

    override fun findBySpecification(specification: Specification<BoardChannel>?, limit: Int?): List<BoardChannel> {
        return boardRepository.findAll(specification, Pageable.ofSize(limit ?: 100)).toList()
    }

    override fun findBySpecificationUsingCursor(
        cursorRequest: CursorPageRequest<BoardChannel>
    ): CursorPage<BoardChannel> {
        return this.findBySpecificationUsingCursorImpl(cursorRequest)
    }

    override fun findBySpecificationUsingPagination(specification: Specification<BoardChannel>?, page: Pageable): Page<BoardChannel> {
        return boardRepository.findAll(specification, page)
    }

    override fun buildNewCursor(cursors: List<Cursor>, sort: Sort, lastEntity: BoardChannel): List<Cursor> {
        return CursorBuilder(
            if (CursorBuilder(cursors).cursors.isEmpty()) {
                sort.map { Cursor(it, 0 as Comparable<Any>) }.toList()
            } else {
                cursors
            }
        ).fromEntity(lastEntity)
    }

}
