package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification

interface ComparePredicateBuilderInterface<T> {
    fun build(value: Comparable<Any>, include: Boolean): Specification<T>

}