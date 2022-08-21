package com.neoguri.neogurinest.api.domain.auth.repository

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository

interface AuthorizationEntityRepositoryInterface : AggregateRootRepository<Authorization, String> {

    fun findByRefreshTokenOrFail(refreshToken: String): Authorization
    
}