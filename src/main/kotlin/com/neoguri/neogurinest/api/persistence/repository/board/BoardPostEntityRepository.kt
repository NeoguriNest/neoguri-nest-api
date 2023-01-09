package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostHashtagRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostRepositoryInterface
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class BoardPostEntityRepository(
    val repository: BoardPostRepositoryInterface,
    val hashTagRepository: BoardPostHashtagRepositoryInterface
) : BoardPostEntityRepositoryInterface {

    override fun save(entity: BoardPost): BoardPost {
        if (!entity.hashTags.isEmpty()) {
            hashTagRepository.saveAll(entity.hashTags)
        }

        repository.save(entity)

        return entity
    }

    override fun findById(id: String): BoardPost? {
        return repository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardPost {
        return findById(id) ?: throw BoardPostNotFoundException()
    }

    override fun findBySpecification(
        specification: Specification<BoardPost>,
        order: Sort.Order,
        limit: Int
    ): List<BoardPost> {

        return repository.findAll(specification, Sort.by(order))
    }

    override fun findBySpecificationUsingPagination(
        specification: Specification<BoardPost>,
        order: Sort.Order,
        page: Pageable
    ): Page<BoardPost> {

        return repository.findAll(specification, page)
    }
}
