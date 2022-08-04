package com.neoguri.neogurinest.api.util

import java.util.*

object StringGenerator {
    fun getUuid(upperCase: Boolean = false): String {

        return if (upperCase) (UUID.randomUUID().toString()).uppercase()
        else (UUID.randomUUID().toString()).lowercase()
    }
}