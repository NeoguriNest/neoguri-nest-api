package com.neoguri.neogurinest.api.application.user.dto.request

data class UserNestAddDto(
    val userId: Int,
    val nestId: Int,
    val village: String,
) {}