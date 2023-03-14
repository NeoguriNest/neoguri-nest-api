package com.neoguri.neogurinest.api.persistence.specification

interface LikeInterface<Type: CharSequence> {
    val value: Type?
}