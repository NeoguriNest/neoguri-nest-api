package com.neoguri.neogurinest.api.util

import org.springframework.util.Base64Utils

object Decoder {

    infix fun decodes(from: String): String {
        return String(Base64Utils.decodeFromUrlSafeString(from))
    }
}