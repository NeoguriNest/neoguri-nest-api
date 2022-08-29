package com.neoguri.neogurinest.api.configuration.security.role

import org.springframework.security.core.GrantedAuthority

class AnonymousRole : GrantedAuthority {
    override fun getAuthority(): String {
        return "Anonymous"
    }
}