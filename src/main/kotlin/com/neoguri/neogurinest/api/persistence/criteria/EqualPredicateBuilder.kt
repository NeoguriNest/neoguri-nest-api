package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.Path

class EqualPredicateBuilder<Entity>(override val field: String, override val negative: Boolean) : AbstractPredicateBuilder(field, negative), EqualPredicateBuilderInterface<Entity> {

    override fun <ValueType> build(value: ValueType): Specification<Entity> {

        return Specification<Entity> { root, query, builder ->
            val path: Path<ValueType> = root.get(field)

            if (negative) {
                builder.equal(path, value)
            } else {
                builder.equal(path, value)
            }
        }
    }
}