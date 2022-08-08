package com.neoguri.neogurinest.api.application.user.dto.response

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.enum.Gender
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class UserDto(
    val userId: Int?,
    val loginId: String?,
    val nickname: String?,
    val email: String?,
    val address: String?,
    val addressDetail: String?,
    val zipCode: String?,
    val sido: String?,
    val sigungu: String?,
    val eupmyeondong: String?,
    val gender: DescribedEnumDto,
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
                entity.nickname,
                entity.email,
                entity.address,
                entity.addressDetail,
                entity.zipCode,
                entity.sido,
                entity.sigungu,
                entity.eupmyeondong,
                DescribedEnumDto(
                    entity.gender!!,
                    when (entity.gender) {
                        Gender.MALE -> "남성"
                        Gender.FEMALE -> "여성"
                        else -> "비공개"
                    }
                ),
                entity.birthdate,
                entity.introductionText,
                if (entity.createdAt === null) null else DateFormatUtil.format(formatText, entity.createdAt!!),
                if (entity.updatedAt === null) null else DateFormatUtil.format(formatText, entity.updatedAt!!)
            )
        }
    }
}