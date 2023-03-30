package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentLike
import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentLikeId
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentLikeEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardCommentLikeRepositoryInterface
import org.springframework.stereotype.Repository

@Repository
class BoardCommentLikeEntityRepository(
    val repository: BoardCommentLikeRepositoryInterface
) : BoardCommentLikeEntityRepositoryInterface {

    override fun save(entity: BoardCommentLike): BoardCommentLike {
        return repository.save(entity)
    }

    override fun findById(id: BoardCommentLikeId): BoardCommentLike? {
        return repository.findById(id).orElse(null)
    }
}
