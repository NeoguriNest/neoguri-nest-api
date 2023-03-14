package com.neoguri.neogurinest.api.persistence.specification

import com.neoguri.neogurinest.api.domain.common.Cursor
import com.neoguri.neogurinest.api.persistence.criteria.GreaterThanOrEqualPredicateBuilder
import com.neoguri.neogurinest.api.persistence.criteria.LessThanOrEqualPredicateBuilder
import org.springframework.data.jpa.domain.Specification

class CursorSpecificationBuilder<E> : AbstractSpecificationBuilder<List<Cursor>, E>() {

    override fun build(from: List<Cursor>): Specification<E>? {
        if (from.isEmpty()) {
            return null
        }

        val list: List<Specification<E>> = from.map {

            if (it.order.isDescending) {
                LessThanOrEqualPredicateBuilder<E>(it.order.property, false).build(it.value, false)
            } else {
                GreaterThanOrEqualPredicateBuilder<E>(it.order.property, false).build(it.value, false)
            }
        }

        return list.reduce { specification, accumulateSpecification ->
            accumulateSpecification.and(specification)
        }
    }

}