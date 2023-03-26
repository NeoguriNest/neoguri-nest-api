package com.neoguri.neogurinest.api.domain.auth.enum;

enum class AuthorizationStatus(val value: Int) {
    EXPIRED(-1),
    AVAILABLE(1);
}
