package com.neoguri.neogurinest.api.application.auth.dto

import com.neoguri.neogurinest.api.domain.user.entity.User

data class LoginUserDto(
    val userId: Int,
    val loginId: String,
    val nestIds: Array<Int>
) {

    companion object {
        fun fromEntity(entity: User): LoginUserDto {

            val nestIds: Array<Int> = entity.nests!!.map { it.nest?.id }.filterNotNull().toTypedArray()

            return LoginUserDto(
                entity.id!!,
                entity.loginId!!,
                nestIds
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginUserDto

        if (userId != other.userId) return false
        if (loginId != other.loginId) return false
        if (!nestIds.contentEquals(other.nestIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId
        result = 31 * result + loginId.hashCode()
        result = 31 * result + nestIds.contentHashCode()
        return result
    }
}