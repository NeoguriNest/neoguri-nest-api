package com.neoguri.neogurinest.api.persistence.criteria

import org.springframework.data.jpa.domain.Specification

interface InPredicateBuilderInterface<T> {
    fun <V> build(values: List<V>): Specification<T>
}