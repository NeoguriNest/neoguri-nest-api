package com.neoguri.neogurinest.api.util

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

object DateFormatUtil {
    fun format(formatString: String, instant: TemporalAccessor): String {
        val formatter = DateTimeFormatter.ofPattern(formatString).withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }
}