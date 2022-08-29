package com.neoguri.neogurinest.api.configuration.security.role

import org.springframework.security.core.GrantedAuthority

class UserRole : GrantedAuthority {
    override fun getAuthority(): String {
        return "USER"
    }
}