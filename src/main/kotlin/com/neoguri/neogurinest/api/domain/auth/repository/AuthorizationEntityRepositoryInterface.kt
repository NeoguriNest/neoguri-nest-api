package com.neoguri.neogurinest.api.domain.auth.repository

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import javax.persistence.EntityNotFoundException

interface AuthorizationEntityRepositoryInterface : AggregateRootRepository<Authorization, String> {

    @Throws(EntityNotFoundException::class)
    fun findByAccessTokenOrFail(accessToken: String): Authorization

    @Throws(EntityNotFoundException::class)
    fun findByRefreshTokenOrFail(refreshToken: String): Authorization

}