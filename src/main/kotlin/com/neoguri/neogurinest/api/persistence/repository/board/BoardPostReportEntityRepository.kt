package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardPostReport
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostReportEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardPostReportRepositoryInterface
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class BoardPostReportEntityRepository(
    val repository: BoardPostReportRepositoryInterface
) : BoardPostReportEntityRepositoryInterface {

    override fun save(entity: BoardPostReport): BoardPostReport {
        return repository.save(entity)
    }

    override fun findById(id: String): BoardPostReport? {
        return repository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardPostReport {
        return findById(id) ?: throw EntityNotFoundException()
    }
}
