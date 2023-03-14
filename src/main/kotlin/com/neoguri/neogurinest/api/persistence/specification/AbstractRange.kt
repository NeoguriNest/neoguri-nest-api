package com.neoguri.neogurinest.api.persistence.specification

import com.neoguri.neogurinest.api.domain.common.exception.LogicException

abstract class AbstractRange<Type: Comparable<Type>>(
    override val start: Type?,
    override val end: Type?,
    override val containsEqual: Boolean = false
) : RangeInterface<Type> {

    override fun getRangeType(): RangeType {
        return if (isBetween()) {
            RangeType.BETWEEN
        } else if (isLessThanTo()) {
            RangeType.LESS_THAN
        } else if (isGreaterThanTo()) {
            RangeType.GREATER_THAN
        } else {
            throw LogicException("예기치 못한 오류 발생")
        }

    }

    override fun isBetween(): Boolean {
        return start !== null && end !== null
    }

    override fun isLessThanTo(): Boolean {
        return start === null && end !== null && !containsEqual
    }

    override fun isGreaterThanTo(): Boolean {
        return start !== null && end === null && !containsEqual
    }

    override fun isContainsEqual(): Boolean {
        return containsEqual
    }
}