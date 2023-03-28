package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostAddDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.service.BoardServiceInterface
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostAddUseCase
import com.neoguri.neogurinest.api.domain.board.entity.BoardHashtag
import com.neoguri.neogurinest.api.domain.board.entity.BoardPost
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostHashtag
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardChannelNotAvailableStatusException
import com.neoguri.neogurinest.api.domain.board.repository.BoardChannelEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardHashtagEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostAdd(
    val boardService: BoardServiceInterface,
    val boardRepository: BoardChannelEntityRepositoryInterface,
    val boardHashtagRepository: BoardHashtagEntityRepositoryInterface,
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostAddUseCase {

    @Retryable(maxAttempts = 3)
    @Transactional
    @Throws(BoardChannelNotFoundException::class, BoardChannelNotAvailableStatusException::class)
    override fun execute(addDto: BoardPostAddDto, actor: BoardActor?): BoardPostDto {

        val board = boardRepository.findByIdOrFail(addDto.channelId)
        if (!board.isPostAddable()) {
            throw BoardChannelNotAvailableStatusException()
        }

        val registeredHashtags = boardHashtagRepository.findByNameIn(addDto.hashTags)
        val newHashtags = createNewHashtag(
            addDto.hashTags.filter { tag -> registeredHashtags.none { it.name.equals(tag) }}
        )

        boardHashtagRepository.saveAll(newHashtags)

        val hashtags = registeredHashtags + newHashtags
        val post = boardPostRepository.save(BoardPost.create(addDto, board, actor))

        val boardPostHashtags: List<BoardPostHashtag> =
            hashtags.map {
                BoardPostHashtag.create(post, it)
            }

        post.hashTags = boardPostHashtags.toMutableList()

        boardService.uploadPost(board)
        // 해시태그 수정 후 재저장
        return BoardPostDto.of(boardPostRepository.save(post))
    }

    private fun createNewHashtag(newHashtags: List<String>): List<BoardHashtag> {
        return newHashtags.map {
            BoardHashtag.create(it)
        }
    }
}