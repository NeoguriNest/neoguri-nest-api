package com.neoguri.neogurinest.api.domain.board.repository

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

interface BoardPostEntityRepositoryInterface: AggregateRootRepository<BoardPost, String> {

    fun findByIdOrFail(id: String): BoardPost

    fun findBySpecification(
        specification: Specification<BoardPost>,
        order: Sort.Order,
        limit: Int
    ): List<BoardPost>
    fun findBySpecificationUsingPagination(
        specification: Specification<BoardPost>,
        order: Sort.Order,
        page: Pageable
    ): Page<BoardPost>
}