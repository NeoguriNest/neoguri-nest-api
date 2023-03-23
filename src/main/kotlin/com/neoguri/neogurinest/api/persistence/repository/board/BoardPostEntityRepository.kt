package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.application.common.dto.CursorPage
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostHashtagRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.Cursor
import com.neoguri.neogurinest.api.domain.common.CursorBuilder
import com.neoguri.neogurinest.api.domain.common.CursorPageRequest
import com.neoguri.neogurinest.api.persistence.specification.CursorSpecificationBuilder
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class BoardPostEntityRepository(
    val repository: BoardPostRepositoryInterface,
    val hashTagRepository: BoardPostHashtagRepositoryInterface
) : BoardPostEntityRepositoryInterface {

    override fun save(entity: BoardPost): BoardPost {
        if (!entity.hashTags.isEmpty()) {
            hashTagRepository.saveAll(entity.hashTags)
        }

        repository.save(entity)

        return entity
    }

    override fun findById(id: String): BoardPost? {
        return repository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardPost {
        return findById(id) ?: throw BoardPostNotFoundException()
    }

    override fun findBySpecification(
        specification: Specification<BoardPost>,
        order: Sort.Order,
        limit: Int
    ): List<BoardPost> {

        return repository.findAll(specification, Sort.by(order))
    }

    override fun countBySpecification(specification: Specification<BoardPost>?): Int {

        return repository.count(specification).toInt()
    }

    override fun findBySpecificationUsingCursorPagination(
        cursorRequest: CursorPageRequest<BoardPost>
    ): CursorPage<BoardPost> {

        val cursors = cursorRequest.cursors
        val cursorSpec = CursorSpecificationBuilder<BoardPost>().build(cursors)

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

    private fun combineSpecifications(vararg specs: Specification<BoardPost>?): Specification<BoardPost>? {
        return if (specs.filterNotNull().isEmpty()) {
            null
        } else {
            specs.filterNotNull().reduce { it, acc -> acc.and(it) }
        }
    }
}
