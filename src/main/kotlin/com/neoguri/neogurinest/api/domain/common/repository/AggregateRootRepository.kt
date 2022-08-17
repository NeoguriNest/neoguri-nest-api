package com.neoguri.neogurinest.api.domain.common.repository

interface AggregateRootRepository<EntityType, KeyType> {
    fun save(entity: EntityType): EntityType

    fun findById(id: KeyType): EntityType?
}