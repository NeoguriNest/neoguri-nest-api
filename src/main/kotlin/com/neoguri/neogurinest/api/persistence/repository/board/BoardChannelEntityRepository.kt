package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardChannelRepositoryInterface
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class BoardChannelEntityRepository(
    val boardRepository: BoardChannelRepositoryInterface
) : BoardChannelEntityRepositoryInterface {

    override fun save(entity: BoardChannel): BoardChannel {
        return boardRepository.save(entity)
    }

    override fun findById(id: String): BoardChannel? {
        return boardRepository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardChannel {
        return findById(id) ?: throw EntityNotFoundException()
    }
}
