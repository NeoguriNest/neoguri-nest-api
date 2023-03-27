package com.neoguri.neogurinest.api.application.nest.usecase

import com.neoguri.neogurinest.api.application.nest.dto.request.NestAddDto
import com.neoguri.neogurinest.api.application.nest.dto.response.NestDto
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException

interface NestAddUseCase {
    @Throws(DuplicatedEntityException::class)
    fun execute(nestAddDto: NestAddDto): NestDto
}