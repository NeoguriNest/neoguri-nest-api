package com.neoguri.neogurinest.api.application.user.dto.response

import com.neoguri.neogurinest.api.domain.user.entity.User

data class UserExistenceProfileDto(
    val userId: Int,
    val email: String
) {

    companion object {
        fun of(entity: User?): UserExistenceProfileDto? {
            if (entity === null) {
                return null
            }

            return UserExistenceProfileDto(
                entity.id!!,
                entity.email!!
            )
        }
    }
}