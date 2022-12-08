package com.neoguri.neogurinest.api.application.nest.dto.request

import com.neoguri.neogurinest.api.domain.nest.enum.NestStatus

data class NestStatusUpdateDto(
    val nestId: Int,
    val status: NestStatus
) {}
