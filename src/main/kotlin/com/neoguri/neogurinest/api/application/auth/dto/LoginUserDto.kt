package com.neoguri.neogurinest.api.application.auth.dto

import com.neoguri.neogurinest.api.domain.auth.entity.Authorization
import com.neoguri.neogurinest.api.domain.user.entity.User

data class LoginUserDto(
    val userId: Int,
    val email: String,
    val nestIds: Array<Int>
) {

    companion object {
        fun of(entity: User, nestIds: Array<Int>): LoginUserDto {
            return LoginUserDto(
                entity.id!!,
                entity.email!!,
                nestIds
            )
        }

        fun of(entity: Authorization): LoginUserDto {
            val nestIdString = entity.nestIds
            var nestIdsArray = arrayOf<Int>()
            if (!nestIdString.isNullOrBlank()) {
                nestIdsArray = nestIdString.split(",").map { it -> Integer.parseInt(it) }.toTypedArray()
            }

            return LoginUserDto(
                entity.userId!!,
                entity.email!!,
                nestIdsArray
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginUserDto

        if (userId != other.userId) return false
        if (email != other.email) return false
        if (!nestIds.contentEquals(other.nestIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId
        result = 31 * result + email.hashCode()
        result = 31 * result + nestIds.contentHashCode()
        return result
    }
}