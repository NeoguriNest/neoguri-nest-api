package com.neoguri.neogurinest.api.application.user.dto.request

data class LoginDto(
    val loginId: String,
    val password: String
) {}