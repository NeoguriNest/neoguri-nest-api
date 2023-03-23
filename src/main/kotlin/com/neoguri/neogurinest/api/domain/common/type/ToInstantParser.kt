package com.neoguri.neogurinest.api.domain.common.type

import org.springframework.format.datetime.standard.TemporalAccessorParser
import org.springframework.stereotype.Component
import java.time.Instant

@Component("toInstant")
class ToInstantParser : ParserInterface {

    override fun parse(value: String): Comparable<*>? {
        return try {
            Instant.parse(value)
        } catch(e: Exception) {
            return null
        }
    }
}