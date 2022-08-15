package com.neoguri.neogurinest.api.application.auth.usecase.impl

import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.application.user.dto.request.LoginDto
import com.neoguri.neogurinest.api.application.user.dto.response.LoginUserDto
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class LoginUseCase(
    val tokenUtil: NeoguriTokenService,
    val userRepository: UserEntityRepositoryInterface,
    val authRepository: AuthorizationEntityRepositoryInterface
) : LoginUseCaseInterface {

    @Throws(UsernameOrPasswordNotMatchedException::class)
    override fun execute(loginDto: LoginDto): AuthorizationDto {
        val user = userRepository.findByLoginId(loginDto.loginId)
        if (user === null) {
            throw UsernameOrPasswordNotMatchedException()
        }

        if (!PasswordEncryptor.matches(user.password!!, loginDto.password)) {
            throw UsernameOrPasswordNotMatchedException()
        }

        val loginUser = LoginUserDto.fromEntity(user)

        val accessToken = tokenUtil.generateAccessToken(loginUser)
        val refreshToken = tokenUtil.generateRefreshToken()

        val authorization = Authorization.create(loginUser, accessToken, refreshToken)
        authRepository.save(authorization)
        return AuthorizationDto.fromEntity(authorization)
    }

}
