package com.neoguri.neogurinest.api.domain.user.repository

import com.neoguri.neogurinest.api.domain.common.repository.AggregateRootRepository
import com.neoguri.neogurinest.api.domain.user.entity.User
import java.util.*

interface UserEntityRepositoryInterface : AggregateRootRepository<User, Int> {

    fun checkExistsByEmail(email: String): Boolean

    fun findByIdOrFail(id: Int): User

    fun findByEmail(email: String): Optional<User>

    fun findByEmailOrFail(email: String): User

}