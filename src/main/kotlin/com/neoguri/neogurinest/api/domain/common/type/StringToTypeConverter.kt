package com.neoguri.neogurinest.api.domain.common.type

import org.springframework.stereotype.Component

@Component
class StringToTypeConverter(
    intParser: ToIntParser,
    floatParser: ToFloatParser,
    instantParser: ToInstantParser
) {
    private val parsers: List<ParserInterface> = arrayListOf(intParser, floatParser, instantParser)

    fun convert(value: String): Comparable<*> {

        var parsedValue: Comparable<*>? = null
        parsers.forEach {
            if ((parsedValue) == null) {
                parsedValue = it.parse(value)
            }
        }

        return parsedValue ?: value
    }

}