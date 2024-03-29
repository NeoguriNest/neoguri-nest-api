package com.neoguri.neogurinest.api.presentation.user

import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserNestAddDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserProfileUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddressUpdateUseCase
import com.neoguri.neogurinest.api.application.user.usecase.UserNestAddUseCase
import com.neoguri.neogurinest.api.application.user.usecase.UserProfileUpdateUseCase
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import com.neoguri.neogurinest.api.presentation.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/api/users/me")
class UserMeController(
    val userAddressUpdate: UserAddressUpdateUseCase,
    val userNestAdd: UserNestAddUseCase,
    val userProfileUpdate: UserProfileUpdateUseCase
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
     * @uri POST /api/users/me/user-nests
     * 소굴 가입
     */
    @PostMapping("/user-nests")
    fun addUserNest(@RequestBody userNestAddDto: UserNestAddDto): ResponseEntity<UserDto> {

        isResourceOwnerOrFail(userNestAddDto.userId)

        return try {
            val userDto = userNestAdd.execute(userNestAddDto)
            ResponseEntity.created(URI("/api/users/me/user-nests/${userDto.userId}")).body(userDto)
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

    /**
     * @uri PUT /api/users/me/profile
     * 프로필 정보 수정
     */
    @PutMapping("/profile")
    fun updateUserProfile(@RequestBody userProfileUpdateDto: UserProfileUpdateDto): ResponseEntity<UserDto> {

        isResourceOwnerOrFail(userProfileUpdateDto.userId)

        return try {
            val userDto = userProfileUpdate.execute(userProfileUpdateDto)
            ResponseEntity.ok(userDto)
        } catch (e: EntityNotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }

}
