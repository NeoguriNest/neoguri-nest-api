package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Path

class GreaterThanOrEqualPredicateBuilder<E>(
    override val field: String,
    override val negative: Boolean
) : AbstractPredicateBuilder(field, negative), ComparePredicateBuilderInterface<E> {

    override fun build(value: Comparable<Any>, include: Boolean): Specification<E> {
        return Specification<E> { root, _, builder ->
            val path: Path<Comparable<Any>> = root.get(field)

            val predicate = if (include) {
                builder.greaterThanOrEqualTo(path, value)
            } else {
                builder.greaterThan(path, value)
            }

            if (negative) {
                predicate.not()
            } else {
                predicate
            }
        }

    }
}