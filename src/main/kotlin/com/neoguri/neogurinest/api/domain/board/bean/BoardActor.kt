package com.neoguri.neogurinest.api.domain.board.bean

import com.neoguri.neogurinest.api.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority

data class BoardActor(
    val user: User,
    val authorities: List<GrantedAuthority>
) {
}