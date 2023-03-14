package com.neoguri.neogurinest.api.domain.common.exception

open class CursorBuildFailedException(override val message: String?) : RuntimeException(message) {
}