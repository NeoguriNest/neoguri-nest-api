package com.neoguri.neogurinest.api.persistence.repository.board

import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentReport
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentReportEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardCommentReportRepositoryInterface
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class BoardCommentReportEntityRepository(
    val repository: BoardCommentReportRepositoryInterface
) : BoardCommentReportEntityRepositoryInterface {

    override fun save(entity: BoardCommentReport): BoardCommentReport {
        return repository.save(entity)
    }

    override fun findById(id: String): BoardCommentReport? {
        return repository.findById(id).orElse(null)
    }

    override fun findByIdOrFail(id: String): BoardCommentReport {
        return findById(id) ?: throw EntityNotFoundException()
    }
}
