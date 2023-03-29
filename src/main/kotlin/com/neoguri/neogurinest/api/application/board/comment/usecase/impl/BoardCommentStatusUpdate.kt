package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentStatusUpdateUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.enum.BoardCommentStatus
import com.neoguri.neogurinest.api.domain.board.exception.BoardCommentStatusNotConvertableException
import com.neoguri.neogurinest.api.domain.board.exception.ModifyingOtherUsersCommentException
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.StatusAlreadyChangedException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class BoardCommentStatusUpdate(
    val repository: BoardCommentEntityRepositoryInterface
) : BoardCommentStatusUpdateUseCase {

    override fun execute(commentId: String, status: BoardCommentStatus, actor: BoardActor): Boolean {
        return executeImpl(commentId, status, actor)
    }

    @Retryable(
        maxAttempts = 3,
        exclude = [
            ModifyingOtherUsersCommentException::class,
            StatusAlreadyChangedException::class,
            BoardCommentStatusNotConvertableException::class
        ]
    )
    @Transactional
    protected fun executeImpl(commentId: String, status: BoardCommentStatus, actor: BoardActor): Boolean {

        val comment = repository.findByIdOrFail(commentId)

        if (actor.user.id != comment.user!!.id) {
            throw ModifyingOtherUsersCommentException()
        }

        if (comment.status == status) {
            throw StatusAlreadyChangedException()
        }

        if (!BoardCommentStatus.getConvertable(status).contains(comment.status)) {
            throw BoardCommentStatusNotConvertableException()
        }

        comment.status = status
        comment.updatedAt = Instant.now()

        repository.save(comment)

        return true
    }
}