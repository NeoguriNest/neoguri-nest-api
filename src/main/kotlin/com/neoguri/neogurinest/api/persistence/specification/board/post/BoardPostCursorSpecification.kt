package com.neoguri.neogurinest.api.persistence.specification.board.post

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.common.Cursor
import com.neoguri.neogurinest.api.persistence.specification.CursorSpecificationBuilder
import org.springframework.data.jpa.domain.Specification


class BoardPostCursorSpecification {

    companion object {
        fun of(list: List<Cursor>): Specification<BoardPost>? {

            return CursorSpecificationBuilder<BoardPost>().build(list)
        }
    }

}