package com.neoguri.neogurinest.api.domain.common.repository

import com.neoguri.neogurinest.api.domain.user.entity.User

interface AggregateRootRepository<EntityType, KeyType> {
    fun findById(id: Int): User?
}