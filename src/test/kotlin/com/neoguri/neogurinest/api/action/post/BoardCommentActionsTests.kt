package com.neoguri.neogurinest.api.action.post

import com.neoguri.neogurinest.api.application.board.action.comment.usecase.BoardCommentLikeAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class BoardCommentActionsTests {

    @Resource
    lateinit var userRepository: UserEntityRepositoryInterface

    @Resource
    lateinit var boardCommentLikeAdd: BoardCommentLikeAddUseCase

    @Test
    fun `댓글 좋아요`() {
        val commentId = "70ce23c8-68d7-414e-9956-b85a5b60aa24"
        val user = userRepository.findByIdOrFail(1)
        val actor = BoardActor(user, emptyList())

        assert(boardCommentLikeAdd.execute(commentId, actor))

    }
}