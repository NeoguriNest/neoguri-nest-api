package com.neoguri.neogurinest.api.configuration.security.provider

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.configuration.security.dto.AccessTokenAuthentication
import com.neoguri.neogurinest.api.configuration.security.role.UserRole
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.exception.AccessTokenExpiredException
import com.neoguri.neogurinest.api.domain.auth.exception.InvalidAccessTokenException
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoField
import javax.persistence.EntityNotFoundException

@Component
class AccessTokenAuthenticationProvider(
    private val authRepository: AuthorizationEntityRepositoryInterface
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        return authenticate(authentication as AccessTokenAuthentication)
    }

    private fun authenticate(authentication: AccessTokenAuthentication): Authentication {
        val now = Instant.now()
        val accessToken = authentication.principal as String

        return try {
            val authorization: Authorization = authRepository.findByAccessTokenOrFail(accessToken)
            if (!now.isBefore(authorization.accessTokenExpiredAt)) {
                throw AccessTokenExpiredException()
            }

            val authUserDto: LoginUserDto = LoginUserDto.of(authorization)

            AccessTokenAuthentication(accessToken, authUserDto, mutableListOf<GrantedAuthority>(UserRole()))
        } catch(e: JpaObjectRetrievalFailureException) {
            if (!EntityNotFoundException::class.java.isAssignableFrom(e.cause!!::class.java)) {
                throw InvalidAccessTokenException()
            }

            authentication
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return AccessTokenAuthentication::class.java.isAssignableFrom(authentication!!)
    }
}