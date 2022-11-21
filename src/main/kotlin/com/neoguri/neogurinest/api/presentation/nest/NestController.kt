package com.neoguri.neogurinest.api.presentation.nest

import com.neoguri.neogurinest.api.application.nest.dto.request.NestAddDto
import com.neoguri.neogurinest.api.application.nest.dto.response.NestDto
import com.neoguri.neogurinest.api.application.nest.usecase.NestAddUseCaseInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import com.neoguri.neogurinest.api.presentation.exception.ConflictException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/nests")
@RestController
class NestController(
    val nestAdd: NestAddUseCaseInterface
) {

    /**
     * @uri POST /api/nests
     * 소굴 추가
     */
    @PostMapping("")
    fun getExistence(nestAddDto: NestAddDto): ResponseEntity<NestDto> {
        return try {
            ResponseEntity.ok(nestAdd.execute(nestAddDto))
        } catch (e: DuplicatedEntityException) {
            throw ConflictException(e.message!!)
        }
    }

}
