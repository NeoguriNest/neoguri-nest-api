package com.neoguri.neogurinest.api.persistence.specification

data class RangeInt(
    override val start: Int?,
    override val end: Int?
) : RangeInterface<Int> {
    override fun isBetween(): Boolean {
        return start !== null && end !== null
    }

    override fun isLessThanEqualTo(): Boolean {
        return start === null && end !== null
    }

    override fun isGreaterThanEqualTo(): Boolean {
        return start !== null && end === null
    }
}