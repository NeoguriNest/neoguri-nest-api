package com.neoguri.neogurinest.api.application.board.comment.usecase.impl

import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentAddDto
import com.neoguri.neogurinest.api.application.board.comment.dto.BoardCommentDto
import com.neoguri.neogurinest.api.application.board.comment.usecase.BoardCommentAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.entity.BoardComment
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotCommentableException
import com.neoguri.neogurinest.api.domain.board.repository.BoardCommentEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.common.exception.DuplicatedEntityException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCommentAdd(
    private val repository: BoardCommentEntityRepositoryInterface,
    private val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardCommentAddUseCase {

    override fun execute(addDto: BoardCommentAddDto, actor: BoardActor): BoardCommentDto {

        return try {
            closure(addDto, actor)
        } catch (e: DataIntegrityViolationException) {
            if (ConstraintViolationException::class.java.isAssignableFrom(e.cause!!::class.java)
                && (e.cause as ConstraintViolationException).constraintName.contains("UNIQUE")) {

                throw DuplicatedEntityException()
            }

            throw e
        }
    }

    @Retryable(maxAttempts = 3, exclude = [ DataIntegrityViolationException::class ])
    @Transactional
    fun closure(addDto: BoardCommentAddDto, actor: BoardActor): BoardCommentDto {

        val post = boardPostRepository.findByIdOrFail(addDto.postId)
        if (!post.status!!.isCommentable()) {
            throw BoardPostStatusNotCommentableException()
        }

        val parent: BoardComment? = if (addDto.commentId == null) {
            null
        } else {
            repository.findById(addDto.commentId)
        }

        val comment = BoardComment.create(actor.user, addDto, post, parent)
        repository.save(comment)
        return BoardCommentDto.of(comment)
    }

}