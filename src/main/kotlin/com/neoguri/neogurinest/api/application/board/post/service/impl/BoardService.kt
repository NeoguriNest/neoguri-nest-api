package com.neoguri.neogurinest.api.application.board.post.service.impl

import com.neoguri.neogurinest.api.application.board.post.service.BoardServiceInterface
import com.neoguri.neogurinest.api.domain.board.entity.BoardChannel
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardChannelRepositoryInterface
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BoardService(
    private val boardRepository: BoardChannelRepositoryInterface
) : BoardServiceInterface {

    override fun uploadPost(board: BoardChannel): BoardChannel {
        board.lastUploadedAt = Instant.now()

        return boardRepository.save(board)
    }
}