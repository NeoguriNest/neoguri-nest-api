package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.Board
import com.neoguri.neogurinest.api.domain.board.repository.BoardEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardRepositoryInterface
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class BoardEntityRepository(
    val boardRepository: BoardRepositoryInterface
) : BoardEntityRepositoryInterface {

    override fun save(entity: Board): Board {
        return boardRepository.save(entity)
    }

    override fun findById(id: String): Board? {
        return boardRepository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): Board {
        return findById(id) ?: throw EntityNotFoundException()
    }
}
