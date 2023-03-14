package com.neoguri.neogurinest.api.persistence.criteria

import com.neoguri.neogurinest.api.persistence.specification.RangeInterface
import org.springframework.data.jpa.domain.Specification

interface BetweenPredicateBuilderInterface<T> {
    fun build(value: RangeInterface<Comparable<Any>>): Specification<T>

}