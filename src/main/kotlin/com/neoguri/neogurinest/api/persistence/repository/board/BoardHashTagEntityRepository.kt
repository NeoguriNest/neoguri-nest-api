package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import com.neoguri.neogurinest.api.domain.board.repository.BoardHashtagEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardHashtagRepositoryInterface
import org.springframework.stereotype.Repository

@Repository
class BoardHashTagEntityRepository(
    val repository: BoardHashtagRepositoryInterface
) : BoardHashtagEntityRepositoryInterface {
    override fun save(entity: BoardHashtag): BoardHashtag {
        return repository.save(entity)
    }

    override fun saveAll(entities: List<BoardHashtag>): List<BoardHashtag> {
        return repository.saveAll(entities)
    }

    override fun findById(id: String): BoardHashtag? {
        return repository.findById(id).orElse(null)
    }

    override fun findByNameIn(names: List<String>): List<BoardHashtag> {
        return repository.findByNameIn(names)
    }

}
