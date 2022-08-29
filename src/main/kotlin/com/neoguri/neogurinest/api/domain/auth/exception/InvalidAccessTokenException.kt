package com.neoguri.neogurinest.api.domain.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidAccessTokenException: AuthenticationException("INVALID_ACCESS_TOKEN") {

}