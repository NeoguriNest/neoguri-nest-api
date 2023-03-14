package com.neoguri.neogurinest.api.persistence.specification

import org.springframework.data.jpa.domain.Specification

class ObjectSpecificationBuilder<C, E>(private val clazz: Class<C>) : AbstractSpecificationBuilder<C, E>() {

    override fun build(from: C): Specification<E>? {

        val list: List<Specification<E>> = clazz.declaredFields.filter {
            it.isAccessible = true
            it.get(from) != null
        }.map {

            val value: Any = it.get(from)
            when {
                isAssignable(RangeInterface::class.java, value) ->
                    range(it.name, value as RangeInterface<Comparable<Any>>)
                LikeInterface::class.java.isAssignableFrom(value.javaClass) ->
                    like(it.name, value as LikeInterface<out CharSequence>)
                List::class.java.isAssignableFrom(value.javaClass) ->
                    `in`(it.name, value as List<Comparable<Any>>)
                else ->
                    equal(it.name, value)
            }
        }

        return if (list.isNotEmpty()) {
            list.reduce { specification, accumulateSpecification ->
                accumulateSpecification.and(specification)
            }
        } else {
            null
        }
    }

}