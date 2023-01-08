package com.neoguri.neogurinest.api.persistence.specification.board.post

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.persistence.specification.NeoguriSpecificationBuilder
import org.springframework.data.jpa.domain.Specification

class BoardPostSpecification {

    companion object {
        fun of(filterDto: BoardPostFilterDto): Specification<BoardPost> {
            return NeoguriSpecificationBuilder.build(filterDto, BoardPostFilterDto::class.java)
        }
    }

}