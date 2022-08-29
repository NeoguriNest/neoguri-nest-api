package com.neoguri.neogurinest.api.application.user.dto.request

data class UserAddressUpdateDto(
    val userId: Int,
    val address: String,
    val addressDetail: String,
    val zipCode: String,
    val sido: String,
    val sigungu: String,
    val eupmyeondong: String
) {}