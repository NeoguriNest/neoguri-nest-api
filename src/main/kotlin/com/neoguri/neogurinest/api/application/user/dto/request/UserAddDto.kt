package com.neoguri.neogurinest.api.application.user.dto.request

import com.neoguri.neogurinest.api.domain.user.enum.Gender

data class UserAddDto(
    val loginId: String,
    val password: String,
    val nickname: String,
    val email: String,
    val address: String,
    val addressDetail: String,
    val zipCode: String,
    val sido: String,
    val sigungu: String,
    val eupmyeondong: String,
    val gender: Gender,
    val birthdate: String,
    val introductionText: String?
) {}