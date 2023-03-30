package com.neoguri.neogurinest.api.application.board.action.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportAddDto
import com.neoguri.neogurinest.api.application.board.action.comment.dto.BoardCommentReportDto
import com.neoguri.neogurinest.api.application.board.action.comment.usecase.BoardCommentReportAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentReport
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotActionableException
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentReportEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCommentReportAdd(
    val repository: BoardCommentReportEntityRepositoryInterface,
    val commentRepository: BoardCommentEntityRepositoryInterface
) : BoardCommentReportAddUseCase {

    override fun execute(addDto: BoardCommentReportAddDto, actor: BoardActor): BoardCommentReportDto {
        return executeImpl(addDto, actor)
    }

    @Retryable(maxAttempts = 3, exclude = [ BoardPostStatusNotActionableException::class ])
    @Transactional
    protected fun executeImpl(addDto: BoardCommentReportAddDto, actor: BoardActor): BoardCommentReportDto {

        val comment = commentRepository.findByIdOrFail(addDto.commentId)
        if (!comment.status!!.isActionable()) {
            throw BoardPostStatusNotActionableException()
        }

        val entity = BoardCommentReport.create(addDto, comment, actor.user)

        repository.save(entity)

        return BoardCommentReportDto.of(entity)
    }
}