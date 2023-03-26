package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.board.exception.BoardCommentNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardCommentRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.Cursor
import com.neoguri.neogurinest.api.domain.common.CursorBuilder
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.CursorSpecificationBuilder
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class BoardCommentEntityRepository(
    val repository: BoardCommentRepositoryInterface
) : BoardCommentEntityRepositoryInterface {

    override fun save(entity: BoardComment): BoardComment {
        repository.save(entity)

        return entity
    }

    override fun findById(id: String): BoardComment? {
        return repository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardComment {
        return findById(id) ?: throw BoardCommentNotFoundException()
    }

    override fun findBySpecification(
        specification: Specification<BoardComment>,
        order: Sort.Order,
        limit: Int
    ): List<BoardComment> {

        return repository.findAll(specification, Sort.by(order))
    }

    override fun countBySpecification(specification: Specification<BoardComment>?): Int {

        return repository.count(specification).toInt()
    }

    override fun findBySpecificationUsingCursorPagination(
        cursorRequest: CursorPageRequest<BoardComment>
    ): CursorPage<BoardComment> {

        val cursors = cursorRequest.cursors
        val cursorSpec = CursorSpecificationBuilder<BoardComment>().build(cursors)

        val filterSpec = cursorRequest.specification
        val specification = combineSpecifications(cursorSpec, filterSpec)
        val totalCount = countBySpecification(filterSpec)
        val cursorCount = countBySpecification(specification)

        return if (cursorCount < 1) {
            CursorPage.emptyPage(totalCount)
        } else {

            val page = repository.findAll(specification, cursorRequest.page)

            val newCursor =
                CursorBuilder(
                    if (CursorBuilder(cursors).cursors.isEmpty()) {
                        cursorRequest.page.sort.map { Cursor(it, 0 as Comparable<Any>) }.toList()
                    } else {
                        cursors
                    }
                ).fromEntity(page.last())

            CursorPage(newCursor, page.toList(), totalCount)
        }
    }

    private fun combineSpecifications(vararg specs: Specification<BoardComment>?): Specification<BoardComment>? {
        return if (specs.filterNotNull().isEmpty()) {
            null
        } else {
            specs.filterNotNull().reduce { it, acc -> acc.and(it) }
        }
    }
}
