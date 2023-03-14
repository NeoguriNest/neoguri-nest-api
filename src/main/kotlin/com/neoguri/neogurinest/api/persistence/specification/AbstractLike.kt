package com.neoguri.neogurinest.api.persistence.specification

abstract class AbstractLike<Type: CharSequence>(override val value: Type): LikeInterface<Type> {

    override fun toString(): String {
        return value.toString()
    }
}