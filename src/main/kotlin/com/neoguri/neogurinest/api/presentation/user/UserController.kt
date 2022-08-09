package com.neoguri.neogurinest.api.presentation.user;

import com.neoguri.neogurinest.api.application.user.dto.request.LoginDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.LoginUserDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.LoginUseCaseInterface
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.domain.user.exception.UsernameOrPasswordNotMatchedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource

@RequestMapping("/api/users")
@RestController
class UserController(
    val login: LoginUseCaseInterface,
    val userAdd: UserAddUseCaseInterface
) {


    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<LoginUserDto> {
        return try {
            val loginUserDto = login.execute(loginDto)
            ResponseEntity.ok(loginUserDto)
        } catch (e: UsernameOrPasswordNotMatchedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody userAddDto: UserAddDto): ResponseEntity<UserDto> {
        return try {
            val userDto = userAdd.execute(userAddDto)
            ResponseEntity.ok(userDto)
        } catch (e: DuplicatedEntityException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }

}
