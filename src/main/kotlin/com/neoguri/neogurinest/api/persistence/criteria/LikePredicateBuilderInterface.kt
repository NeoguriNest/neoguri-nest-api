package com.neoguri.neogurinest.api.persistence.criteria

import com.neoguri.neogurinest.api.persistence.specification.LikeInterface
import org.springframework.data.jpa.domain.Specification

interface LikePredicateBuilderInterface<T> {
    fun build(value: LikeInterface<out CharSequence>): Specification<T>

}