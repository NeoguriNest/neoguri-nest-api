package com.neoguri.neogurinest.api.controller.user;

import com.neoguri.neogurinest.api.application.user.dto.UserAddDto
import com.neoguri.neogurinest.api.application.user.dto.UserDto
import com.neoguri.neogurinest.api.application.user.usecase.UserAddUseCaseInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource

@RequestMapping("/api/users")
@RestController
class UserController {

    @Resource
    val userAdd: UserAddUseCaseInterface? = null;

    @PostMapping("/register")
    fun register(@RequestBody userAddDto: UserAddDto): ResponseEntity<UserDto> {

        val userDto = userAdd!!.execute(userAddDto)

        return ResponseEntity.ok(userDto)
    }

}
