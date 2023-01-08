package com.neoguri.neogurinest.api.persistence.specification

interface RangeInterface<Type: Comparable<Type>> {
    val start: Type?
    val end: Type?

    fun isBetween(): Boolean
    fun isLessThanEqualTo(): Boolean
    fun isGreaterThanEqualTo(): Boolean

}