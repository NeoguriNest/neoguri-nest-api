package com.neoguri.neogurinest.api.application.user.dto.response

import com.neoguri.neogurinest.api.application.common.dto.DescribedEnumDto
import com.neoguri.neogurinest.api.domain.user.entity.User
import com.neoguri.neogurinest.api.domain.user.enum.Gender
import com.neoguri.neogurinest.api.util.DateFormatUtil

data class UserDto(
    val userId: Int,
    val email: String,
    val nickname: String?,
    val address: String?,
    val addressDetail: String?,
    val zipCode: String?,
    val sido: String?,
    val sigungu: String?,
    val eupmyeondong: String?,
    val gender: DescribedEnumDto<Gender>,
    val birthdate: String?,
    val introductionText: String?,
    val createdAt: String,
    val updatedAt: String?
) {

    companion object {
        fun of(entity: User): UserDto {

            return UserDto(
                entity.id!!,
                entity.email!!,
                entity.nickname,
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
                        Gender.NONE -> "비공개"
                        else -> "-"
                    }
                ),
                entity.birthdate,
                entity.introductionText,
                entity.createdAt!!.toString(),
                entity.updatedAt?.toString()
            )
        }
    }
}