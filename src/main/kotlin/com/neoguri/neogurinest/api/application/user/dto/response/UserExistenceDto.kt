package com.neoguri.neogurinest.api.application.user.dto.response

import com.neoguri.neogurinest.api.domain.user.entity.User

data class UserExistenceDto(
    val exists: Boolean,
    val user: UserExistenceProfileDto?,
) {

    companion object {
        fun ofUnregistered(): UserExistenceDto {
            return UserExistenceDto(
                false,
                null
            )
        }

        fun of(entity: User): UserExistenceDto {
            return UserExistenceDto(
                true,
                UserExistenceProfileDto.of(entity)
            )
        }
    }
}