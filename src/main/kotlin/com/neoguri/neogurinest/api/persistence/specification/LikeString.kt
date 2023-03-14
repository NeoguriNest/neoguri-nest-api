package com.neoguri.neogurinest.api.persistence.specification

data class LikeString(
    override val value: String
) : AbstractLike<String>(value) {

}