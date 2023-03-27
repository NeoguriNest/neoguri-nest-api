package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.dto.BoardActor
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetUseCase
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class BoardPostGet(
    val boardPostRepository: BoardPostEntityRepositoryInterface
): BoardPostGetUseCase {

    @Throws(BoardPostNotFoundException::class)
    override fun execute(postId: String, actor: BoardActor?): BoardPostDto {

        return try {
            val post = boardPostRepository.findByIdOrFail(postId)

            // TODO: Actor 정보에 따라 Action을 조회하도록 구현
            BoardPostDto.of(post)
        } catch (e: EntityNotFoundException) {
            throw BoardPostNotFoundException()
        }
    }
}
