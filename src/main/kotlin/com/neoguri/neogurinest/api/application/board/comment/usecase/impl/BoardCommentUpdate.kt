package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentUpdateDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentUpdateUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotCommentableException
import com.neoguri.neogurinest.api.domain.board.exception.ModifyingOtherUsersCommentException
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCommentUpdate(
    val repository: BoardCommentEntityRepositoryInterface
) : BoardCommentUpdateUseCase {

    override fun execute(commentId: String, updateDto: BoardCommentUpdateDto, actor: BoardActor): BoardCommentDto {

        return closure(commentId, updateDto, actor)
    }

    @Retryable(maxAttempts = 3)
    @Transactional
    protected fun closure(commentId: String, updateDto: BoardCommentUpdateDto, actor: BoardActor): BoardCommentDto {

        val comment = repository.findByIdOrFail(commentId)
        if (comment.user!!.id != actor.id) {
            throw ModifyingOtherUsersCommentException()
        }

        val post = comment.post!!

        if (!post.status!!.isCommentable()) {
            throw BoardPostStatusNotCommentableException()
        }

        comment.updateContent(updateDto.content)
        repository.save(comment)

        return BoardCommentDto.of(comment)
    }
}