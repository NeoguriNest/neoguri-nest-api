package com.neoguri.neogurinest.api.domain.auth.exception

import org.springframework.security.core.AuthenticationException

class AccessTokenExpiredException: AuthenticationException("ACCESS_TOKEN_EXPIRED") {

}