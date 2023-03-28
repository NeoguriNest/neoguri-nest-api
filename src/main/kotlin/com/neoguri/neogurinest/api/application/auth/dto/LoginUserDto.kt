package com.neoguri.neogurinest.api.application.auth.dto

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.user.entity.User

data class LoginUserDto(
    val userId: Int,
    val email: String,
    val nestIds: List<Int>
) {

    companion object {
        fun of(entity: User, nestIds: List<Int>): LoginUserDto {
            return LoginUserDto(
                entity.id!!,
                entity.email!!,
                nestIds
            )
        }

        fun of(entity: Authorization): LoginUserDto {
            val nestIdString = entity.nestIds
            var nestIdsArray: List<Int> = arrayListOf()
            if (!nestIdString.isNullOrBlank()) {
                nestIdsArray = nestIdString.split(",").map { it -> Integer.parseInt(it) }.toList()
            }

            return LoginUserDto(
                entity.userId!!,
                entity.email!!,
                nestIdsArray
            )
        }
    }
}