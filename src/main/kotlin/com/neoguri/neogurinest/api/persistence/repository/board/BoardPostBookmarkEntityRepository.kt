package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmark
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostBookmarkId
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostBookmarkEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostBookmarkRepositoryInterface
import org.springframework.stereotype.Repository

@Repository
class BoardPostBookmarkEntityRepository(
    val repository: BoardPostBookmarkRepositoryInterface
) : BoardPostBookmarkEntityRepositoryInterface {

    override fun save(entity: BoardPostBookmark): BoardPostBookmark {
        return repository.save(entity)
    }

    override fun findById(id: BoardPostBookmarkId): BoardPostBookmark? {
        return repository.findById(id).orElse(null)
    }
}
