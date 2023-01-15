package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardChannelRepositoryInterface
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

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
        return findById(id) ?: throw BoardChannelNotFoundException()
    }

    override fun findBySpecification(specification: Specification<BoardChannel>, limit: Int?): List<BoardChannel> {
        return boardRepository.findAll(specification, Pageable.ofSize(limit ?: 100)).toList()
    }
}
