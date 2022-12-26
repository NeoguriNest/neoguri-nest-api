package com.neoguri.neogurinest.api.application.auth.usecase.impl

import com.neoguri.neogurinest.api.application.auth.dto.AuthorizationDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginDto
import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.auth.service.NeoguriTokenService
import com.neoguri.neogurinest.api.application.auth.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.auth.exception.UsernameOrPasswordNotMatchedException
import com.neoguri.neogurinest.api.domain.auth.repository.AuthorizationEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import org.springframework.stereotype.Service

@Service
class LoginUseCase(
    val tokenUtil: NeoguriTokenService,
    val userRepository: UserEntityRepositoryInterface,
    val authRepository: AuthorizationEntityRepositoryInterface
) : LoginUseCaseInterface {

    @Throws(UsernameOrPasswordNotMatchedException::class)
    override fun execute(loginDto: LoginDto): AuthorizationDto {
        val optionalUser = userRepository.findByEmail(loginDto.loginId)
        if (optionalUser.isEmpty) {
            throw UsernameOrPasswordNotMatchedException()
        }

        val user: User = optionalUser.get()
        if (!PasswordEncryptor.matches(user.password!!, loginDto.password)) {
            throw UsernameOrPasswordNotMatchedException()
        }

        val nestIds: Array<Int> = user.nests.map { it.nest?.id }.filterNotNull().toTypedArray()

        val loginUser = LoginUserDto.of(user, nestIds)

        val accessToken = tokenUtil.generateAccessToken(loginUser)
        val refreshToken = tokenUtil.generateRefreshToken()

        val authorization = Authorization.create(loginUser, accessToken, refreshToken)
        authRepository.save(authorization)
        return AuthorizationDto.of(authorization)
    }

}
