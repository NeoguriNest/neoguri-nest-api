package com.neoguri.neogurinest.api.application.user.usecase.impl

import com.neoguri.neogurinest.api.application.user.dto.request.LoginDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.LoginUserDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import com.neoguri.neogurinest.api.util.PasswordEncryptor
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class LoginUseCase(
    val userRepository: UserEntityRepositoryInterface
) : LoginUseCaseInterface {

    @Throws(UsernameOrPasswordNotMatchedException::class)
    override fun execute(loginDto: LoginDto): LoginUserDto {
        val user = userRepository.findByLoginId(loginDto.loginId)
        if (user === null) {
            throw UsernameOrPasswordNotMatchedException()
        }

        if (!PasswordEncryptor.matches(user.password!!, loginDto.password)) {
            throw UsernameOrPasswordNotMatchedException()
        }

        return LoginUserDto.fromEntity(user)
    }

}
