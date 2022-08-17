package com.neoguri.neogurinest.api.util

import java.security.SecureRandom
import java.util.*
import kotlin.streams.toList

object StringGenerator {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getUuid(upperCase: Boolean = false): String {

        return if (upperCase) (UUID.randomUUID().toString()).uppercase()
        else (UUID.randomUUID().toString()).lowercase()
    }

    fun random(length: Int): String {
        return SecureRandom()
            .ints(length.toLong(), 0, charPool.size)
            .toList()
            .map { charPool[it] }
            .joinToString(separator = "")
    }
}