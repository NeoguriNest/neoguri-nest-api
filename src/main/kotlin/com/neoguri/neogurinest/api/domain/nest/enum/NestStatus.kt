package com.neoguri.neogurinest.api.domain.nest.enum

enum class NestStatus(val value: Int) {
    CREATED(0),
    ACTIVATED(1),
    SUSPENDED(-1),
    DELETED(-2)
}