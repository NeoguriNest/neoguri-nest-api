package com.neoguri.neogurinest.api.application.board.post.service.impl

import com.neoguri.neogurinest.api.application.board.post.service.BoardServiceInterface
import com.neoguri.neogurinest.api.domain.board.entity.Board
import com.neoguri.neogurinest.api.domain.board.repository.jpa.BoardRepositoryInterface
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BoardService(
    private val boardRepository: BoardRepositoryInterface
) : BoardServiceInterface {

    override fun uploadPost(board: Board): Board {
        board.lastUploadedAt = Instant.now()

        return boardRepository.save(board)
    }
}