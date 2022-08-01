package com.neoguri.neogurinest.api.domain.user.enum

enum class UserNestStatus(val value: Int) {
    BANNED(-2),
    LEFT(-1),
    JOINED(0)
}