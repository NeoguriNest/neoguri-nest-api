package com.neoguri.neogurinest.api.domain.common.type

interface ParserInterface {
    fun parse(value: String): Comparable<*>?
}