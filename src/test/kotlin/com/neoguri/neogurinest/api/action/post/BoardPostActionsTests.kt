package com.neoguri.neogurinest.api.action.post

import com.neoguri.neogurinest.api.application.board.action.post.dto.BoardPostReportAddDto
import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostLikeAddUseCase
import com.neoguri.neogurinest.api.application.board.action.post.usecase.BoardPostReportAddUseCase
import com.neoguri.neogurinest.api.domain.board.bean.BoardActor
import com.neoguri.neogurinest.api.domain.board.enum.BoardPostReportType
import com.neoguri.neogurinest.api.domain.user.repository.UserEntityRepositoryInterface
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.nio.charset.Charset
import javax.annotation.Resource

@SpringBootTest
class BoardPostActionsTests {

    @Resource
    lateinit var boardPostLikeAdd: BoardPostLikeAddUseCase

    @Resource
    lateinit var boardPostReportAdd: BoardPostReportAddUseCase

    @Resource
    lateinit var userRepository: UserEntityRepositoryInterface

    @Test
    fun `게시글 좋아요`() {
        val postId = "e45cc5f2-42ad-4485-a045-cd2d1a306135"
        val user = userRepository.findByIdOrFail(1)
        val actor = BoardActor(user, emptyList())

        assert(boardPostLikeAdd.execute(postId, actor))

    }
    
    @Test
    fun `게시글 신고`() {
        val postId = "e45cc5f2-42ad-4485-a045-cd2d1a30613d"
        val type = BoardPostReportType.ETC
        val content = String("도배성 글인것 같아서 신고하겠습니다.".toByteArray(), Charset.defaultCharset())

        val user = userRepository.findByIdOrFail(1)
        val actor = BoardActor(user, emptyList())

        boardPostReportAdd.execute(
            BoardPostReportAddDto(
                postId,
                type,
                content
            ),
            actor
        )
    }
}