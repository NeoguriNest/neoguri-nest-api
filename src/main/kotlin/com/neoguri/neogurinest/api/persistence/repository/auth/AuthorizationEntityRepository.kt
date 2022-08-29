package com.neoguri.neogurinest.api.persistence.repository.auth

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.enum.AuthorizationStatus
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.auth.repository.jpa.AuthorizationRepositoryInterface
import org.springframework.data.domain.Example
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.jvm.Throws

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

    override fun findByAccessTokenOrFail(accessToken: String): Authorization {
        val example = Authorization()
        example.accessToken = accessToken

        val authorization: Optional<Authorization> = repository.findOne(Example.of(example))
        if (!authorization.isPresent) {
            throw EntityNotFoundException(Authorization::class.simpleName)
        }

        return authorization.get()
    }

    override fun findByRefreshTokenOrFail(refreshToken: String): Authorization {
        val example = Authorization()
        example.refreshToken = refreshToken

        val authorization: Optional<Authorization> = repository.findOne(Example.of(example))
        if (!authorization.isPresent) {
            throw EntityNotFoundException(Authorization::class.simpleName)
        }

        return authorization.get()
    }
}