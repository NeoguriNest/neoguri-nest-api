package com.neoguri.neogurinest.api.application.auth.usecase.impl

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.auth.dto.RefreshDto
import com.neoguri.neogurinest.api.application.auth.usecase.RefreshUseCaseInterface
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.exception.InvalidRefreshTokenException
import com.neoguri.neogurinest.api.domain.auth.exception.RefreshTokenExpiredException
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RefreshUseCase(
    val tokenUtil: NeoguriTokenService,
    val authRepository: AuthorizationEntityRepositoryInterface
) : RefreshUseCaseInterface {

    @Throws(RefreshTokenExpiredException::class)
    override fun execute(refreshDto: RefreshDto): AuthorizationDto {

        try {
            val authorization: Authorization = authRepository.findByRefreshTokenOrFail(refreshDto.refreshToken)

            val newAccessToken = tokenUtil.generateAccessToken(LoginUserDto.of(authorization))

            authorization.updateAccessToken(
                newAccessToken.token,
                newAccessToken.expiresAt,
            )

            authRepository.save(authorization)

            return AuthorizationDto.of(authorization)
        } catch (e: EntityNotFoundException) {
            throw InvalidRefreshTokenException()
        }
    }
}