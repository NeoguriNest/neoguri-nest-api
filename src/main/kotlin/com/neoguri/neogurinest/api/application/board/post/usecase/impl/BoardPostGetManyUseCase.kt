package com.neoguri.neogurinest.api.application.board.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostActorDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostDto
import com.neoguri.neogurinest.api.application.board.post.dto.BoardPostFilterDto
import com.neoguri.neogurinest.api.application.board.post.usecase.BoardPostGetManyUseCaseInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.persistence.specification.board.post.BoardPostSpecification
import org.springframework.data.domain.Sort
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostGetManyUseCase(
    val boardPostRepository: BoardPostEntityRepositoryInterface
) : BoardPostGetManyUseCaseInterface {

    @Retryable(maxAttempts = 3)
    @Transactional
    override fun execute(filter: BoardPostFilterDto, actor: BoardPostActorDto?): List<BoardPostDto> {
        val specification = BoardPostSpecification.of(filter)
        // Default
        val order = Sort.Order(Sort.Direction.DESC, "createdAt")
        val boardPosts = boardPostRepository.findBySpecification(specification, order, 500)

        return boardPosts.map { BoardPostDto.of(it) }

    }

}