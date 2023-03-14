package com.neoguri.neogurinest.api.persistence.specification

import com.neoguri.neogurinest.api.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

abstract class AbstractSpecificationBuilder<F, E>: SpecificationBuilderInterface<F, E> {

    abstract override fun build(from: F): Specification<E>?

    protected fun isAssignable(clazz: Class<*>, value: Any): Boolean {
        return clazz.isAssignableFrom(value.javaClass)
    }

    protected fun equal(field: String, value: Any): Specification<E> {
        return EqualPredicateBuilder<E>(field, false).build(value)
    }

    protected fun range(field: String, value:  RangeInterface<Comparable<Any>>): Specification<E> {

        return when ((value as RangeInterface<*>).getRangeType()) {
            RangeType.BETWEEN -> BetweenPredicateBuilder<E>(field, false).build(value)
            RangeType.LESS_THAN -> LessThanOrEqualPredicateBuilder<E>(field, false).build(value.end!!, value.containsEqual)
            RangeType.GREATER_THAN -> GreaterThanOrEqualPredicateBuilder<E>(field, false).build(value.start!!, value.containsEqual)
        }
    }

    protected fun like(field: String, value: LikeInterface<out CharSequence>): Specification<E> {
        return LikePredicateBuilder<E>(field).build(value)
    }

    protected fun `in`(field: String, value: List<Comparable<Any>>): Specification<E> {
        return InPredicateBuilder<E>(field, false).build(value)
    }

}