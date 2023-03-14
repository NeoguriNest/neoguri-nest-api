package com.neoguri.neogurinest.api.persistence.criteria

import com.neoguri.neogurinest.api.persistence.specification.RangeInterface
import org.springframework.data.jpa.domain.Specification
import java.time.Instant
import javax.persistence.criteria.Path

class BetweenPredicateBuilder<T>(override val field: String, override val negative: Boolean) : AbstractPredicateBuilder(field, negative), BetweenPredicateBuilderInterface<T> {

    override fun build(value: RangeInterface<Comparable<Any>>): Specification<T> {
        return Specification<T> { root, query, builder ->
            val path: Path<Comparable<Any>> = root.get(field)

            val predicate = builder.between(path, value.start!!, value.end!!)
            if (negative) {
                predicate.not()
            } else {
                predicate
            }
        }
    }

}