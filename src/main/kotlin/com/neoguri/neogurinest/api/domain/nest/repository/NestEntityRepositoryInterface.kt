package com.neoguri.neogurinest.api.domain.nest.repository

import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import com.neoguri.neogurinest.api.domain.nest.entity.Nest

interface NestEntityRepositoryInterface : AggregateRootRepository<Nest, Int> {

    fun findByIdOrFail(id: Int): Nest

}