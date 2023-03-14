package com.neoguri.neogurinest.api.persistence.specification

interface RangeInterface<Type: Comparable<Type>> {
    val start: Type?
    val end: Type?
    val containsEqual: Boolean

    fun getRangeType(): RangeType
    fun isBetween(): Boolean
    fun isLessThanTo(): Boolean
    fun isGreaterThanTo(): Boolean
    fun isContainsEqual(): Boolean

}