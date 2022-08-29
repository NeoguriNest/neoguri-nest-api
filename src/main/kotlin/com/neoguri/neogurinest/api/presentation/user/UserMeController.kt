package com.neoguri.neogurinest.api.presentation.user;

import com.neoguri.neogurinest.api.application.auth.dto.LoginUserDto
import com.neoguri.neogurinest.api.application.user.dto.request.UserAddressUpdateDto
import com.neoguri.neogurinest.api.application.user.dto.response.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddressUpdateUseCaseInterface
import com.neoguri.neogurinest.api.presentation.BaseController
import com.neoguri.neogurinest.api.presentation.exception.ForbiddenException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/me")
class UserMeController(val userAddressUpdate: UserAddressUpdateUseCaseInterface) : BaseController() {

    @PutMapping("/address")
    fun register(@RequestBody addressUpdateDto: UserAddressUpdateDto): ResponseEntity<UserDto> {
        val authentication: Authentication = getAuthentication()
        if (authentication.details === null
            || !LoginUserDto::class.java.isAssignableFrom(authentication.details::class.java)
            || (authentication.details as LoginUserDto).userId != addressUpdateDto.userId
        ) {
            throw ForbiddenException("Invalid permission")
        }

        val userDto = userAddressUpdate.execute(addressUpdateDto)
        return ResponseEntity.ok(userDto)
    }

}
