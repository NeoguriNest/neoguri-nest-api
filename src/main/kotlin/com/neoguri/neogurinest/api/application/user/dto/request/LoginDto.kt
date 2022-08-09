package com.neoguri.neogurinest.api.application.user.dto.request

import com.neoguri.neogurinest.api.domain.user.enum.Gender

data class LoginDto(
    val loginId: String,
    val password: String
) {}