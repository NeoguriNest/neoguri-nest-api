package com.neoguri.neogurinest.api.persistence.specification.board.comment

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentFilterDto
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.persistence.specification.ObjectSpecificationBuilder
import org.springframework.data.jpa.domain.Specification

class BoardCommentSpecification {
    companion object {

        fun of(filterDto: BoardCommentFilterDto): Specification<BoardComment>? {
            return ObjectSpecificationBuilder<BoardCommentFilterDto, BoardComment>(BoardCommentFilterDto::class.java).build(filterDto)
        }
    }
}