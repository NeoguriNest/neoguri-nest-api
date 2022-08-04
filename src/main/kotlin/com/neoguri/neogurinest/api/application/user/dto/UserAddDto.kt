package com.neoguri.neogurinest.api.application.user.dto

import com.neoguri.neogurinest.api.domain.user.enum.Gender

data class UserAddDto(
    val loginId: String,
    val password: String,
    val nickname: String,
    val email: String,
    val gender: Gender,
    val birthdate: String,
    val introductionText: String?
) {}