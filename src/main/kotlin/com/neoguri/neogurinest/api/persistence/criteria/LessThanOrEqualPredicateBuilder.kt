package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Path

class LessThanOrEqualPredicateBuilder<E>(
    override val field: String,
    override val negative: Boolean
) : AbstractPredicateBuilder(field, negative), ComparePredicateBuilderInterface<E> {

    override fun build(value: Comparable<Any>, include: Boolean): Specification<E> {
        return Specification<E> { root, _, builder ->
            val path: Path<Comparable<Any>> = root.get(field)

            if (include) {
                builder.lessThanOrEqualTo(path, value)
            } else {
                builder.lessThan(path, value)
            }
        }

    }
}