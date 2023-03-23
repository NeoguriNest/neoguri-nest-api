package com.neoguri.neogurinest.api.domain.common.type

import org.springframework.stereotype.Component

@Component("toFloat")
class ToFloatParser : ParserInterface {

    override fun parse(value: String): Comparable<*>? {
        return value.toFloatOrNull()
    }
}