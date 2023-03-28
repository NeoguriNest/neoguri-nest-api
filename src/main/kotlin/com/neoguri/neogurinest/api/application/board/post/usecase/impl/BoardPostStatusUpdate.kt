package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostStatusUpdateUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostStatusUpdate(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostStatusUpdateUseCase {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class)
    override fun execute(statusUpdateDto: BoardPostStatusUpdateDto, actor: BoardActor): BoardPostDto {

        return closure(statusUpdateDto)
    }

    @Retryable(maxAttempts = 3)
    @Transactional
    protected fun closure(statusUpdateDto: BoardPostStatusUpdateDto): BoardPostDto {
        val post = boardPostRepository.findByIdOrFail(statusUpdateDto.postId)

        val board = post.channel
        if (!board!!.isPostAddable()) {
            throw BoardChannelNotAvailableStatusException()
        }

        post.updateStatus(statusUpdateDto.status)

        return BoardPostDto.of(boardPostRepository.save(post))
    }

}