package com.neoguri.neogurinest.api.application.board.action.post.usecase.impl

import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostLikeAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.entity.BoardPostLike
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostNotFoundException
import com.neoguri.neogurinest.api.domain.board.exception.BoardPostStatusNotActionableException
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostEntityRepositoryInterface
import com.neoguri.neogurinest.api.domain.board.repository.BoardPostLikeEntityRepositoryInterface
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardPostLikeAdd(
    val repository: BoardPostLikeEntityRepositoryInterface,
    val postRepository: BoardPostEntityRepositoryInterface
) : BoardPostLikeAddUseCase {

    @Throws(
        BoardPostNotFoundException::class,
        BoardPostStatusNotActionableException::class
    )
    override fun execute(postId: String, actor: BoardActor): Boolean {
        return executeImpl(postId, actor)
    }

    @Retryable(maxAttempts = 3, exclude = [ BoardPostStatusNotActionableException::class ])
    @Transactional
    protected fun executeImpl(postId: String, actor: BoardActor): Boolean {

        val post = postRepository.findByIdOrFail(postId)
        if (!post.status!!.isActionable()) {
            throw BoardPostStatusNotActionableException()
        }

        val entity = BoardPostLike.create(post, actor)

        repository.save(entity)
        return true
    }
}