package com.neoguri.neogurinest.api.presentation.user

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserNestAddDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddressUpdateUseCaseInterface
import com.neoguri.neogurinest.api.application.user.usecase.UserNestAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/me")
class UserMeController(
    val userAddressUpdate: UserAddressUpdateUseCaseInterface,
    val userNestAdd: UserNestAddUseCaseInterface
) : BaseController() {

    /**
     * @uri PUT /api/users/me/address
     * 주소 수정
     */
    @PutMapping("/address")
    fun updateAddress(@RequestBody addressUpdateDto: UserAddressUpdateDto): ResponseEntity<UserDto> {

        isResourceOwnerOrFail(addressUpdateDto.userId)

        val userDto = userAddressUpdate.execute(addressUpdateDto)
        return ResponseEntity.ok(userDto)
    }

    /**
     * @uri POST /api/users/me/nests
     * 소굴 가입
     */
    @PostMapping("/nests")
    fun addUserNest(@RequestBody userNestAddDto: UserNestAddDto): ResponseEntity<UserDto> {

        isResourceOwnerOrFail(userNestAddDto.userId)

        return try {
            val userDto = userNestAdd.execute(userNestAddDto)
            ResponseEntity.ok(userDto)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

}
