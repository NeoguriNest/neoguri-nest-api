package com.neoguri.neogurinest.api.domain.user.repository

import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import com.neoguri.neogurinest.api.domain.user.entity.User

interface UserEntityRepositoryInterface : AggregateRootRepository<User, Int> {

    fun findByIdOrFail(id: Int): User

    fun findByLoginId(loginId: String): User?
}