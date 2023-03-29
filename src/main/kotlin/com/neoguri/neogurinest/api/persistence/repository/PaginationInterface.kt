package com.neoguri.neogurinest.api.persistence.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface PaginationInterface<T> {

    fun findBySpecificationUsingPagination(specification: Specification<T>?, page: Pageable): Page<T>

    fun countBySpecification(specification: Specification<T>?): Int
}
