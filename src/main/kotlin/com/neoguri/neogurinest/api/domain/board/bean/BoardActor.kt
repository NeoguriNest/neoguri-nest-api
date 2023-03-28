package com.neoguri.neogurinest.api.domain.board.bean

import org.springframework.security.core.GrantedAuthority

data class BoardActor(
    val id: Int,
    val nickname: String?,
    val nestId: List<Int>,
    val authorities: List<GrantedAuthority>
) {
}