package com.neoguri.neogurinest.api.domain.common.exception

open class LogicException(override val message: String?) : RuntimeException(message) {
}