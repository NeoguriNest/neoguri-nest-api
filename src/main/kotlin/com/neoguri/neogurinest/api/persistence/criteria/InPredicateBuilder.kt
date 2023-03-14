package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification

class InPredicateBuilder<T>(
    override val field: String,
    override val negative: Boolean
) : AbstractPredicateBuilder(field, negative), InPredicateBuilderInterface<T> {
    override fun <V> build(values: List<V>): Specification<T> {

        return Specification<T> { root, _, _ ->

            root.get<V>(field).`in`(values)
        }
    }
}