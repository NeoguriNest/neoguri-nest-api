package com.neoguri.neogurinest.api.application.nest.usecase

import com.neoguri.neogurinest.api.application.nest.dto.request.NestStatusUpdateDto
import com.neoguri.neogurinest.api.application.nest.dto.response.NestDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface NestStatusUpdateUseCase {
    @Throws(DuplicatedEntityException::class)
    fun execute(updateDto: NestStatusUpdateDto): NestDto
}