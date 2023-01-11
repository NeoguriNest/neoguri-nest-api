package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostStatusUpdateDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostStatusUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostStatus
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostCannotUpdateException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostStatusUpdateUseCase(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostStatusUpdateUseCaseInterface {

    @Throws(BoardPostNotFoundException::class, BoardPostCannotUpdateException::class)
    override fun execute(statusUpdateDto: BoardPostStatusUpdateDto): BoardPostDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun(statusUpdateDto: BoardPostStatusUpdateDto): BoardPostDto {
                val post = boardPostRepository.findByIdOrFail(statusUpdateDto.postId)

                val board = post.channel
                if (!board!!.isPostAddable()) {
                    throw BoardChannelNotAvailableStatusException()
                }

                post.updateStatus(statusUpdateDto.status)
//                if (BoardPostStatus.DELETED === statusUpdateDto.status) {
//                    // TODO: 삭제 시 Hashtag 또는 게시글 집계 데이터 수정
//                }

                return BoardPostDto.of(boardPostRepository.save(post))
            }

        return closure(statusUpdateDto)
    }

    private fun createNewHashtag(newHashtags: List<String>): List<BoardHashtag> {
        return newHashtags.map {
            BoardHashtag.create(it)
        }
    }
}