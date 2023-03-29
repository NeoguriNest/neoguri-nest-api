package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostStatusUpdateUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.ModifyingOtherUsersPostException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostStatusUpdate(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostStatusUpdateUseCase {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class, ModifyingOtherUsersPostException::class)
    override fun execute(postId: String, statusUpdateDto: BoardPostStatusUpdateDto, actor: BoardActor): BoardPostDto {

        return executeImpl(postId, statusUpdateDto, actor)
    }

    @Retryable(maxAttempts = 3)
    @Transactional
    protected fun executeImpl(postId: String, statusUpdateDto: BoardPostStatusUpdateDto, actor: BoardActor): BoardPostDto {
        val post = boardPostRepository.findByIdOrFail(postId)
        if (post.userId != actor.user.id) {
            throw ModifyingOtherUsersPostException()
        }

        val board = post.channel
        if (!board!!.isPostAddable()) {
            throw BoardChannelNotAvailableStatusException()
        }

        post.updateStatus(statusUpdateDto.status)

        return BoardPostDto.of(boardPostRepository.save(post))
    }

}