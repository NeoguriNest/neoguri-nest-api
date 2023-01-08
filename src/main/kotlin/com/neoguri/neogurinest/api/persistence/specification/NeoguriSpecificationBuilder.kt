package com.neoguri.neogurinest.api.persistence.specification

import com.neoguri.neogurinest.api.domain.common.exception.LogicException
import com.neoguri.neogurinest.api.persistence.exception.UnexpectedSpecificationTypeException
import org.springframework.data.jpa.domain.Specification
import java.lang.reflect.Field
import java.time.Instant
import javax.persistence.criteria.Path

object NeoguriSpecificationBuilder : NeoguriSpecificationBuilderInterface {

    override fun <Filter, Entity> build(filter: Filter, clazz: Class<Filter>): Specification<Entity> {
        val list: List<Specification<Entity>> = clazz.declaredFields.filter {
            it.isAccessible = true
            it.get(filter) != null
        }.map {

            val value: Any = it.get(filter)
            when {
                RangeInterface::class.java.isAssignableFrom(value.javaClass) ->
                    range<Entity> (value, it)
                else ->
                    equal<Entity> (value, it)
            }
        }

        return list.reduce { specification, accumulateSpecification ->
            accumulateSpecification.and(specification)
        }
    }

    private fun <Entity> equal(value: Any, field: Field): Specification<Entity> {

        return try {
            toPredicateEqual<Entity, Any> (value as Comparable<*>, field)
        } catch (e: Exception) {
            throw UnexpectedSpecificationTypeException(value::class.java)
        }
    }


    private fun <Entity> range(value: Any, field: Field): Specification<Entity> {

        return if (RangeInstant::class.java.isAssignableFrom(value::class.java)) {
            toPredicateRange<Entity, Instant> (value as RangeInstant, field)
        } else if (RangeInt::class.java.isAssignableFrom(value::class.java)) {
            toPredicateRange<Entity, Int> (value as RangeInt, field)
        } else {
            throw UnexpectedSpecificationTypeException(value::class.java)
        }
    }

    private fun <Entity, T : Comparable<T>> toPredicateRange(compareValue: RangeInterface<T>, field: Field): Specification<Entity> {

        return Specification<Entity> { root, query, builder ->
            val path: Path<T> = root.get(field.name)

            if (compareValue.isBetween()) {
                builder.between(path, compareValue.start!!, compareValue.end!!)
            } else if (compareValue.isGreaterThanEqualTo()) {
                builder.greaterThanOrEqualTo(path, compareValue.start!!)
            } else if (compareValue.isLessThanEqualTo()){
                builder.lessThanOrEqualTo(path, compareValue.end!!)
            } else {
                throw LogicException("No matched range criteria build type")
            }

        }
    }

    private fun <Entity, T> toPredicateEqual(compareValue: Comparable<*>, field: Field): Specification<Entity> {

        return Specification<Entity> { root, query, builder ->
            val path: Path<T> = root.get(field.name)

            builder.equal(path, compareValue)
        }
    }


//    protected fun whereIn(obj: Filter, field: Field) {
//        val fieldValue = (field.get(obj) as List<*>)
//
//        Specification<Entity> { root, query, builder ->
//
//            if (fieldValue.start === null) {
//                builder.lessThanOrEqualTo(root.get(field.name), fieldValue.end as Instant)
//            } else if (fieldValue.end === null) {
//                builder.greaterThanOrEqualTo(root.get(field.name), fieldValue.start as Instant)
//            } else {
//                builder.between(root.get(field.name), fieldValue.start as Instant, fieldValue.end  as Instant)
//            }
//
//        }
//
//    }

}