package com.neoguri.neogurinest.api.application.board.action.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.action.comment.usecase.BoardCommentLikeAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.entity.BoardCommentLike
import com.neoguri.neogurinest.api.domain.board.exception.*
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentLikeEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCommentLikeAdd(
    val repository: BoardCommentLikeEntityRepositoryInterface,
    val commentRepository: BoardCommentEntityRepositoryInterface
) : BoardCommentLikeAddUseCase {

    @Throws(
        BoardCommentNotFoundException::class,
        BoardCommentActionAlreadyExistException::class,
        BoardCommentStatusNotActionableException::class
    )
    override fun execute(commentId: String, actor: BoardActor): Boolean {
        return executeImpl(commentId, actor)
    }

    @Retryable(maxAttempts = 3, exclude = [ DuplicatedEntityException::class, BoardPostStatusNotActionableException::class ])
    @Transactional
    protected fun executeImpl(commentId: String, actor: BoardActor): Boolean {

        val comment = commentRepository.findByIdOrFail(commentId)
        if (!comment.status!!.isActionable()) {
            throw BoardCommentStatusNotActionableException()
        }

        val entity = BoardCommentLike.create(comment, actor)

        repository.save(entity)
        return true
    }
}