package com.neoguri.neogurinest.api.persistence.specification

import org.springframework.data.jpa.domain.Specification

interface NeoguriSpecificationBuilderInterface {

    fun <Filter, Entity> build(filter: Filter, clazz: Class<Filter>): Specification<Entity>
}