package com.neoguri.neogurinest.api.domain.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidRefreshTokenException: AuthenticationException("Invalid refresh token") {

}