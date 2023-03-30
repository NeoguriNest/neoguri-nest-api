package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLike
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLikeId
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostLikeEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostLikeRepositoryInterface
import org.springframework.stereotype.Repository

@Repository
class BoardPostLikeEntityRepository(
    val repository: BoardPostLikeRepositoryInterface
) : BoardPostLikeEntityRepositoryInterface {

    override fun save(entity: BoardPostLike): BoardPostLike {
        return repository.save(entity)
    }

    override fun findById(id: BoardPostLikeId): BoardPostLike? {
        return repository.findById(id).orElse(null)
    }
}
