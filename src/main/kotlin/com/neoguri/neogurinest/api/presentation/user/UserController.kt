package com.neoguri.neogurinest.api.presentation.user;

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
class UserController(val userAdd: UserAddUseCaseInterface) {

    val logger: Logger = LoggerFactory.getLogger(this@UserController::class.java)
    @PostMapping("/register")
    fun register(@RequestBody userAddDto: UserAddDto): ResponseEntity<UserDto> {
        return try {
            val userDto = userAdd.execute(userAddDto)
            ResponseEntity.ok(userDto)
        } catch (e: DuplicatedEntityException) {
            logger.info(e.message)
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }

}