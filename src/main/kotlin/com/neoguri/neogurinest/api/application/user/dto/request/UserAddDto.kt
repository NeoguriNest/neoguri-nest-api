package com.neoguri.neogurinest.api.application.user.dto.request

import com.neoguri.neogurinest.api.domain.user.enum.Gender

data class UserAddDto(
    val email: String,
    val password: String,
    val nickname: String,
    val gender: Gender,
    val birthdate: String,
    val introductionText: String?
) {}