package com.neoguri.neogurinest.api.application.user.dto

import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.enum.Gender
import com.neoguri.neogurinest.api.util.DateFormatUtil
import java.time.format.DateTimeFormatter

data class UserDto(
    val userId: Int?,
    val loginId: String?,
    val password: String?,
    val nickname: String?,
    val email: String?,
    val gender: Gender?,
    val birthdate: String?,
    val introductionText: String?,
    val createdAt: String?,
    val updatedAt: String?
) {

    companion object {
        fun fromEntity(entity: User): UserDto {
            val formatText = "yyyy-MM-dd hh:mm:ss"
            return UserDto(
                entity.id,
                entity.loginId,
                entity.password,
                entity.nickname,
                entity.email,
                entity.gender,
                entity.birthdate,
                entity.introductionText,
                if (entity.createdAt === null) null else DateFormatUtil.format(formatText, entity.createdAt!!),
                if (entity.updatedAt === null) null else DateFormatUtil.format(formatText, entity.updatedAt!!)
            )
        }
    }
}