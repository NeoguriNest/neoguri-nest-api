package com.neoguri.neogurinest.api.persistence.repository.auth

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.auth.repository.jpa.AuthorizationRepositoryInterface
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AuthorizationEntityRepository(
    val repository: AuthorizationRepositoryInterface
) : AuthorizationEntityRepositoryInterface {

    override fun save(entity: Authorization): Authorization {
        return repository.save(entity)
    }

    override fun findById(id: String): Authorization? {
        return repository.findByIdOrNull(id)
    }
}