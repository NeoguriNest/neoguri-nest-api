package com.neoguri.neogurinest.api.persistence.specification

import java.time.Instant

data class RangeInstant(
    override val start: Instant?,
    override val end: Instant?
) : RangeInterface<Instant> {
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