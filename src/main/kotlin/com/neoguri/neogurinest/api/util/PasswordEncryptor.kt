package com.neoguri.neogurinest.api.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordEncryptor {
    private val encoder = BCryptPasswordEncoder();

    fun encrypt(text: String): String {
        return encoder.encode(text);
    }

    fun matches(encryptedString: String, rawString: String): Boolean {
        return encoder.matches(rawString, encryptedString);
    }
}