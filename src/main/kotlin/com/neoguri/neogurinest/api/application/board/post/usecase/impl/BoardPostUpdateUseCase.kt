package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostUpdateDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostUpdateUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostHashtag
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardHashtagEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostUpdateUseCase(
    val boardRepository: BoardChannelEntityRepositoryInterface,
    val boardHashtagRepository: BoardHashtagEntityRepositoryInterface,
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostUpdateUseCaseInterface {

    @Throws(BoardChannelNotFoundException::class, BoardPostNotFoundException::class, BoardChannelNotAvailableStatusException::class)
    override fun execute(updateDto: BoardPostUpdateDto): BoardPostDto {

        val closure =
            @Retryable(maxAttempts = 3)
            @Transactional
            fun(updateDto: BoardPostUpdateDto): BoardPostDto {
                val post = boardPostRepository.findByIdOrFail(updateDto.postId)

                val targetBoardId = updateDto.boardId ?: post.channelId
                val board = boardRepository.findByIdOrFail(targetBoardId!!)
                if (!board.isPostAddable()) {
                    throw BoardChannelNotAvailableStatusException()
                }

                val registeredHashtags = boardHashtagRepository.findByNameIn(updateDto.hashTags)
                val newHashtags = createNewHashtag(
                    updateDto.hashTags.filter { tag -> registeredHashtags.none { it.name.equals(tag) }}
                )
                boardHashtagRepository.saveAll(newHashtags)

                val hashtags = registeredHashtags + newHashtags
                val boardPostHashtags =
                    hashtags.map {
                        BoardPostHashtag.create(post, it)
                    }

                post.update(updateDto, board, boardPostHashtags)
                return BoardPostDto.of(boardPostRepository.save(post))
            }

        return closure(updateDto)
    }

    private fun createNewHashtag(newHashtags: List<String>): List<BoardHashtag> {
        return newHashtags.map {
            BoardHashtag.create(it)
        }
    }
}