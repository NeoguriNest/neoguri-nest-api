package com.neoguri.neogurinest.api.presentation.user

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserExistenceCheckDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserExistenceDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import com.neoguri.neogurinest.api.application.user.usecase.UserExistenceCheckUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/users")
@RestController
class UserController(
    val userExistenceCheck: UserExistenceCheckUseCaseInterface,
    val userAdd: UserAddUseCaseInterface,
) {

    /**
     * @uri GET /api/users/exists
     * 유저 가입 여부 조회
     */
    @GetMapping("/exists")
    fun getExistence(checkDto: UserExistenceCheckDto): ResponseEntity<UserExistenceDto> {
        return try {
            ResponseEntity.ok(userExistenceCheck.execute(checkDto))
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

    /**
     * @uri POST /api/users/sign-up
     * 회원가입
     */
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
