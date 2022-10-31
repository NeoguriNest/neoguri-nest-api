package com.neoguri.neogurinest.api.presentation.user;

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserController(val userAdd: UserAddUseCaseInterface) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody userAddDto: UserAddDto): ResponseEntity<UserDto> {
        return try {
            val userDto = userAdd.execute(userAddDto)
            ResponseEntity.ok(userDto)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

}
