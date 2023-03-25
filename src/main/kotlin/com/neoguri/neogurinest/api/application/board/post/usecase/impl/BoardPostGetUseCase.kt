package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostActorDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class BoardPostGetUseCase(
    val boardPostRepository: BoardPostEntityRepositoryInterface
): BoardPostGetUseCaseInterface {

    @Throws(BoardPostNotFoundException::class)
    override fun execute(postId: String, actor: BoardPostActorDto?): BoardPostDto {

        return try {
            val post = boardPostRepository.findByIdOrFail(postId)

            // TODO: Actor 정보에 따라 Action을 조회하도록 구현
            BoardPostDto.of(post)
        } catch (e: EntityNotFoundException) {
            throw BoardPostNotFoundException()
        }
    }
}
